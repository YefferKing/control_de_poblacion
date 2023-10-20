package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Eps;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EpsRepository extends PagingAndSortingRepository<Eps, Long> {
}
