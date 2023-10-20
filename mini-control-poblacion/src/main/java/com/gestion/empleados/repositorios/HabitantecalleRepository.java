package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.HabitanteCalle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.*;

public interface HabitantecalleRepository extends PagingAndSortingRepository<HabitanteCalle, Long> {
    @Query("SELECT h.consecutivo FROM HabitanteCalle h ORDER BY h.consecutivo DESC")
    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    @Query("SELECT h FROM HabitanteCalle h WHERE " +
            "(:input IS NULL OR " +
            "h.primerNombre = :input OR " +
            "h.segundoNombre = :input OR " +
            "h.primerApellido = :input OR " +
            "h.segundoApellido = :input)")
    List<HabitanteCalle> findByAtributos(String input);
}
