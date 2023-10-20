package com.gestion.empleados.servicio;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Eps;
import com.gestion.empleados.repositorios.EmpleadoRepository;
import com.gestion.empleados.repositorios.EpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EpsServiceImpl implements EpsService{

    @Autowired
    private EpsRepository epsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Eps> findAll() {
        return (List<Eps>) epsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Eps> findAll(Pageable pageable) {
        return epsRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Eps eps) {
        epsRepository.save(eps);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        epsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Eps findOne(Long id) {
        return epsRepository.findById(id).orElse(null);
    }

}
