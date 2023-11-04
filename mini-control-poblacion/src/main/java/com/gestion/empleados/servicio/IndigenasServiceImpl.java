package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.Indigenas;
import com.gestion.empleados.entidades.PoblacionPrivada;
import com.gestion.empleados.repositorios.HabitantecalleRepository;
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
public class IndigenasServiceImpl implements IndigenasService {

    @Autowired
    private IndigenasRepository indigenasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Indigenas> findAll() {
        return (List<Indigenas>) indigenasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Indigenas> findAll(Pageable pageable) {
        return indigenasRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Indigenas indigenas) {
        indigenasRepository.save(indigenas);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        indigenasRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Indigenas findOne(Long id) {
        return indigenasRepository.findById(id).orElse(null);
    }

    @Override
    public String obtenerUltimoConsecutivoDesdeBaseDeDatos() {
        // Utiliza el repositorio para obtener el último HabitanteCalle ordenado por consecutivo en orden descendente
        Iterable<Indigenas> indigenas = indigenasRepository.findAll(Sort.by(Sort.Direction.DESC, "consecutivo"));

        List<Indigenas> indigenasList = new ArrayList<>();
        indigenas.forEach(indigenasList::add);

        if (!indigenasList.isEmpty()) {
            Indigenas ultimoIndigena = indigenasList.get(0);
            String consecutivoActual = ultimoIndigena.getConsecutivo();
            int numero = Integer.parseInt(consecutivoActual.substring(consecutivoActual.length() - 4)); // Obtiene los últimos tres dígitos

            // Verifica si el habitante de calle es nuevo (ID es nulo)
            if (ultimoIndigena.getId() != null) {
                numero++; // Incrementa el número solo para nuevos registros
            }

            String nuevoNumero = String.format("%03d", numero); // Formatea el nuevo número con ceros a la izquierda
            String nuevoConsecutivo = consecutivoActual.substring(0, consecutivoActual.length() - 3) + nuevoNumero; // Construye el nuevo consecutivo

            // Actualiza el consecutivo en el último registro solo si es un nuevo registro
            if (ultimoIndigena.getId() == null) {
                ultimoIndigena.setConsecutivo(nuevoConsecutivo);
                indigenasRepository.save(ultimoIndigena);
            }

            return nuevoConsecutivo;
        } else {
            // Si no hay habitantes de calle registrados todavía, regresa un valor predeterminado
            return "54001I0001"; // Ajusta este valor según tus necesidades
        }
    }

    public List<Indigenas> findByAtributos(String primerNombre) {
        return indigenasRepository.findByAtributos(primerNombre);
    }

    @Override
    public Indigenas findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento) {
        return indigenasRepository.findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
                primerNombre, segundoNombre, primerApellido, segundoApellido, fechaNacimiento);
    }
}
