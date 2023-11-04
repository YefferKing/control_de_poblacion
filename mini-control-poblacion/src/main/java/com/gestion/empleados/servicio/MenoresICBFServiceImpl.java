package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Indigenas;
import com.gestion.empleados.entidades.MenoresICBF;
import com.gestion.empleados.entidades.PoblacionPrivada;
import com.gestion.empleados.repositorios.IndigenasRepository;
import com.gestion.empleados.repositorios.MenoresICBFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenoresICBFServiceImpl implements MenoresICBFService {

    @Autowired
    private MenoresICBFRepository menoresICBFRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MenoresICBF> findAll() {
        return (List<MenoresICBF>) menoresICBFRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenoresICBF> findAll(Pageable pageable) {
        return menoresICBFRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(MenoresICBF menoresICBF) {
        menoresICBFRepository.save(menoresICBF);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        menoresICBFRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MenoresICBF findOne(Long id) {
        return menoresICBFRepository.findById(id).orElse(null);
    }

    @Override
    public String obtenerUltimoConsecutivoDesdeBaseDeDatos() {
        // Utiliza el repositorio para obtener el último HabitanteCalle ordenado por consecutivo en orden descendente
        Iterable<MenoresICBF> menoresICBF = menoresICBFRepository.findAll(Sort.by(Sort.Direction.DESC, "consecutivo"));

        List<MenoresICBF> menoresICBFList = new ArrayList<>();
        menoresICBF.forEach(menoresICBFList::add);

        if (!menoresICBFList.isEmpty()) {
            MenoresICBF ultimoMenoresICBF = menoresICBFList.get(0);
            String consecutivoActual = ultimoMenoresICBF.getConsecutivo();
            int numero = Integer.parseInt(consecutivoActual.substring(consecutivoActual.length() - 4)); // Obtiene los últimos tres dígitos

            // Verifica si el habitante de calle es nuevo (ID es nulo)
            if (ultimoMenoresICBF.getId() != null) {
                numero++; // Incrementa el número solo para nuevos registros
            }

            String nuevoNumero = String.format("%03d", numero); // Formatea el nuevo número con ceros a la izquierda
            String nuevoConsecutivo = consecutivoActual.substring(0, consecutivoActual.length() - 3) + nuevoNumero; // Construye el nuevo consecutivo

            // Actualiza el consecutivo en el último registro solo si es un nuevo registro
            if (ultimoMenoresICBF.getId() == null) {
                ultimoMenoresICBF.setConsecutivo(nuevoConsecutivo);
                menoresICBFRepository.save(ultimoMenoresICBF);
            }

            return nuevoConsecutivo;
        } else {
            // Si no hay habitantes de calle registrados todavía, regresa un valor predeterminado
            return "54001A0001"; // Ajusta este valor según tus necesidades
        }
    }

    public List<MenoresICBF> findByAtributos(String primerNombre) {
        return menoresICBFRepository.findByAtributos(primerNombre);
    }

    @Override
    public MenoresICBF findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento) {
        return menoresICBFRepository.findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
                primerNombre, segundoNombre, primerApellido, segundoApellido, fechaNacimiento);
    }
}
