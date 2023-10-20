package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Entidades;
import com.gestion.empleados.entidades.Eps;
import com.gestion.empleados.repositorios.EntidadesRepository;
import com.gestion.empleados.repositorios.EpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntidadesServiceImpl implements EntidadesService {

    @Autowired
    private EntidadesRepository entidadesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Entidades> findAll() {
        return (List<Entidades>) entidadesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entidades> findAll(Pageable pageable) {
        return entidadesRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Entidades entidades) {
        entidadesRepository.save(entidades);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entidadesRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Entidades findOne(Long id) {
        return entidadesRepository.findById(id).orElse(null);
    }
}
