package com.gestion.empleados.servicio;


import com.gestion.empleados.entidades.Adultos;
import com.gestion.empleados.entidades.Indigenas;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface AdultosService {

    public List<Adultos> findAll();

    public Page<Adultos> findAll(Pageable pageable);

    public void save(Adultos adultos);

    public Adultos findOne(Long id);

    public void delete(Long id);

    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    List<Adultos> findByAtributos(String primerNombre);

    Adultos findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento);

}
