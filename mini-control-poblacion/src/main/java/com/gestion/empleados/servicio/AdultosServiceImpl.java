package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Adultos;
import com.gestion.empleados.entidades.Indigenas;
import com.gestion.empleados.entidades.PoblacionPrivada;
import com.gestion.empleados.repositorios.AdultosRepository;
import com.gestion.empleados.repositorios.IndigenasRepository;
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
public class AdultosServiceImpl implements AdultosService {

    @Autowired
    private AdultosRepository adultosRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Adultos> findAll() {
        return (List<Adultos>) adultosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Adultos> findAll(Pageable pageable) {
        return adultosRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Adultos adultos) {
        adultosRepository.save(adultos);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        adultosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Adultos findOne(Long id) {
        return adultosRepository.findById(id).orElse(null);
    }

    @Override
    public String obtenerUltimoConsecutivoDesdeBaseDeDatos() {
        // Utiliza el repositorio para obtener el último HabitanteCalle ordenado por consecutivo en orden descendente
        Iterable<Adultos> adultos = adultosRepository.findAll(Sort.by(Sort.Direction.DESC, "consecutivo"));

        List<Adultos> adultosList = new ArrayList<>();
        adultos.forEach(adultosList::add);

        if (!adultosList.isEmpty()) {
            Adultos ultimoAdulto = adultosList.get(0);
            String consecutivoActual = ultimoAdulto.getConsecutivo();
            int numero = Integer.parseInt(consecutivoActual.substring(consecutivoActual.length() - 4)); // Obtiene los últimos tres dígitos

            // Verifica si el habitante de calle es nuevo (ID es nulo)
            if (ultimoAdulto.getId() != null) {
                numero++; // Incrementa el número solo para nuevos registros
            }

            String nuevoNumero = String.format("%03d", numero); // Formatea el nuevo número con ceros a la izquierda
            String nuevoConsecutivo = consecutivoActual.substring(0, consecutivoActual.length() - 3) + nuevoNumero; // Construye el nuevo consecutivo

            // Actualiza el consecutivo en el último registro solo si es un nuevo registro
            if (ultimoAdulto.getId() == null) {
                ultimoAdulto.setConsecutivo(nuevoConsecutivo);
                adultosRepository.save(ultimoAdulto);
            }

            return nuevoConsecutivo;
        } else {
            // Si no hay habitantes de calle registrados todavía, regresa un valor predeterminado
            return "54001S0001"; // Ajusta este valor según tus necesidades
        }
    }

    public List<Adultos> findByAtributos(String primerNombre) {
        return adultosRepository.findByAtributos(primerNombre);
    }

    @Override
    public Adultos findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento) {
        return adultosRepository.findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
                primerNombre, segundoNombre, primerApellido, segundoApellido, fechaNacimiento);
    }
}
