package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface PPLRepository extends PagingAndSortingRepository<PoblacionPrivada, Long> {
    @Query("SELECT h.consecutivo FROM PoblacionPrivada h ORDER BY h.consecutivo DESC")
    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    @Query("SELECT h FROM PoblacionPrivada h WHERE " +
            "(:input IS NULL OR " +
            "h.primerNombre = :input OR " +
            "h.segundoNombre = :input OR " +
            "h.primerApellido = :input OR " +
            "h.segundoApellido = :input)")
    List<PoblacionPrivada> findByAtributos(String input);

    PoblacionPrivada findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento);
}
