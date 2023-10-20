package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.repositorios.HabitantecalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;


import java.util.ArrayList;
import java.util.List;

@Service
public class HabitantecalleServiceImpl implements HabitantecalleService {

    @Autowired
    private HabitantecalleRepository habitantecalleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HabitanteCalle> findAll() {
        return (List<HabitanteCalle>) habitantecalleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HabitanteCalle> findAll(Pageable pageable) {
        return habitantecalleRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(HabitanteCalle habitanteCalle) {
        habitantecalleRepository.save(habitanteCalle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        habitantecalleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitanteCalle findOne(Long id) {
        return habitantecalleRepository.findById(id).orElse(null);
    }

    @Override
    public String obtenerUltimoConsecutivoDesdeBaseDeDatos() {
        // Utiliza el repositorio para obtener el último HabitanteCalle ordenado por consecutivo en orden descendente
        Iterable<HabitanteCalle> habitantes = habitantecalleRepository.findAll(Sort.by(Sort.Direction.DESC, "consecutivo"));

        List<HabitanteCalle> habitantesList = new ArrayList<>();
        habitantes.forEach(habitantesList::add);

        if (!habitantesList.isEmpty()) {
            HabitanteCalle ultimoHabitante = habitantesList.get(0);
            String consecutivoActual = ultimoHabitante.getConsecutivo();
            int numero = Integer.parseInt(consecutivoActual.substring(consecutivoActual.length() - 4)); // Obtiene los últimos tres dígitos

            // Verifica si el habitante de calle es nuevo (ID es nulo)
            if (ultimoHabitante.getId() != null) {
                numero++; // Incrementa el número solo para nuevos registros
            }

            String nuevoNumero = String.format("%03d", numero); // Formatea el nuevo número con ceros a la izquierda
            String nuevoConsecutivo = consecutivoActual.substring(0, consecutivoActual.length() - 3) + nuevoNumero; // Construye el nuevo consecutivo

            // Actualiza el consecutivo en el último registro solo si es un nuevo registro
            if (ultimoHabitante.getId() == null) {
                ultimoHabitante.setConsecutivo(nuevoConsecutivo);
                habitantecalleRepository.save(ultimoHabitante);
            }

            return nuevoConsecutivo;
        } else {
            // Si no hay habitantes de calle registrados todavía, regresa un valor predeterminado
            return "54001D0001"; // Ajusta este valor según tus necesidades
        }
    }

    public List<HabitanteCalle> findByAtributos(String primerNombre) {
        return habitantecalleRepository.findByAtributos(primerNombre);
    }


}
