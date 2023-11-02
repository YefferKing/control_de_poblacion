package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PPLService {

    public List<PoblacionPrivada> findAll();

    public Page<PoblacionPrivada> findAll(Pageable pageable);

    public void save(PoblacionPrivada poblacionPrivada);

    public PoblacionPrivada findOne(Long id);

    public void delete(Long id);

    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    List<PoblacionPrivada> findByAtributos(String primerNombre);

}
