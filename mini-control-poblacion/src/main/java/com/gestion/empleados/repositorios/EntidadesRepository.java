package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Entidades;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EntidadesRepository extends PagingAndSortingRepository<Entidades, Long> {
}
