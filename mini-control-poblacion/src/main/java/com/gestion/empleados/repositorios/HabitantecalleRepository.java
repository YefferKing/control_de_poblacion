package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.HabitanteCalle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HabitantecalleRepository extends PagingAndSortingRepository<HabitanteCalle, Long> {
    @Query("SELECT h.consecutivo FROM HabitanteCalle h ORDER BY h.consecutivo DESC")
    String obtenerUltimoConsecutivoDesdeBaseDeDatos();
}
