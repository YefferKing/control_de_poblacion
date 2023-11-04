package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Indigenas;
import com.gestion.empleados.entidades.MenoresICBF;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface MenoresICBFService {

    public List<MenoresICBF> findAll();

    public Page<MenoresICBF> findAll(Pageable pageable);

    public void save(MenoresICBF menoresICBF);

    public MenoresICBF findOne(Long id);

    public void delete(Long id);

    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    List<MenoresICBF> findByAtributos(String primerNombre);

    MenoresICBF findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento);
}
