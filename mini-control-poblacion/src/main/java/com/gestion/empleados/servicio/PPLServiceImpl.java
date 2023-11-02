package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.PoblacionPrivada;
import com.gestion.empleados.repositorios.HabitantecalleRepository;
import com.gestion.empleados.repositorios.PPLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PPLServiceImpl implements PPLService {

    @Autowired
    private PPLRepository pplRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PoblacionPrivada> findAll() {
        return (List<PoblacionPrivada>) pplRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PoblacionPrivada> findAll(Pageable pageable) {
        return pplRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(PoblacionPrivada poblacionPrivada) {
        pplRepository.save(poblacionPrivada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pplRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PoblacionPrivada findOne(Long id) {
        return pplRepository.findById(id).orElse(null);
    }

    @Override
    public String obtenerUltimoConsecutivoDesdeBaseDeDatos() {
        // Utiliza el repositorio para obtener el último HabitanteCalle ordenado por consecutivo en orden descendente
        Iterable<PoblacionPrivada> poblacionPrivada = pplRepository.findAll(Sort.by(Sort.Direction.DESC, "consecutivo"));

        List<PoblacionPrivada> pplList = new ArrayList<>();
        poblacionPrivada.forEach(pplList::add);

        if (!pplList.isEmpty()) {
            PoblacionPrivada ultimoPPL = pplList.get(0);
            String consecutivoActual = ultimoPPL.getConsecutivo();
            int numero = Integer.parseInt(consecutivoActual.substring(consecutivoActual.length() - 4)); // Obtiene los últimos tres dígitos

            // Verifica si el habitante de calle es nuevo (ID es nulo)
            if (ultimoPPL.getId() != null) {
                numero++; // Incrementa el número solo para nuevos registros
            }

            String nuevoNumero = String.format("%03d", numero); // Formatea el nuevo número con ceros a la izquierda
            String nuevoConsecutivo = consecutivoActual.substring(0, consecutivoActual.length() - 3) + nuevoNumero; // Construye el nuevo consecutivo

            // Actualiza el consecutivo en el último registro solo si es un nuevo registro
            if (ultimoPPL.getId() == null) {
                ultimoPPL.setConsecutivo(nuevoConsecutivo);
                pplRepository.save(ultimoPPL);
            }

            return nuevoConsecutivo;
        } else {
            // Si no hay habitantes de calle registrados todavía, regresa un valor predeterminado
            return "54001P0001"; // Ajusta este valor según tus necesidades
        }
    }

    public List<PoblacionPrivada> findByAtributos(String primerNombre) {
        return pplRepository.findByAtributos(primerNombre);
    }

}
