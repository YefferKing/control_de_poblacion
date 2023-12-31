package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.Indigenas;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IndigenasService {

    public List<Indigenas> findAll();

    public Page<Indigenas> findAll(Pageable pageable);

    public void save(Indigenas indigenas);

    public Indigenas findOne(Long id);

    public void delete(Long id);

    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    List<Indigenas> findByAtributos(String primerNombre);

    Indigenas findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento);
}
