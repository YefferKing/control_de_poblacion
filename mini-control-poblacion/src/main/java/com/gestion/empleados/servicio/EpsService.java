package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Eps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EpsService {

    public List<Eps> findAll();

    public Page<Eps> findAll(Pageable pageable);

    public void save(Eps eps);

    public Eps findOne(Long id);

    public void delete(Long id);
}
