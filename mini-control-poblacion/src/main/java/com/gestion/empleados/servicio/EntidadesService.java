package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Entidades;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntidadesService {

    public List<Entidades> findAll();

    public Page<Entidades> findAll(Pageable pageable);

    public void save(Entidades entidades);

    public Entidades findOne(Long id);

    public void delete(Long id);
}
