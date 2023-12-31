package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface HabitantecalleService {

    public List<HabitanteCalle> findAll();

    public Page<HabitanteCalle> findAll(Pageable pageable);

    public void save(HabitanteCalle habitanteCalle);

    public HabitanteCalle findOne(Long id);

    public void delete(Long id);

    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    List<HabitanteCalle> findByAtributos(String primerNombre);

    HabitanteCalle findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento);
}
