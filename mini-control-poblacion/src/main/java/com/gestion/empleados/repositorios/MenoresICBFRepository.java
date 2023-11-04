package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.MenoresICBF;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface MenoresICBFRepository extends PagingAndSortingRepository<MenoresICBF, Long> {
    @Query("SELECT h.consecutivo FROM MenoresICBF h ORDER BY h.consecutivo DESC")
    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    @Query("SELECT h FROM MenoresICBF h WHERE " +
            "(:input IS NULL OR " +
            "h.primerNombre = :input OR " +
            "h.segundoNombre = :input OR " +
            "h.primerApellido = :input OR " +
            "h.segundoApellido = :input)")
    List<MenoresICBF> findByAtributos(String input);

    MenoresICBF findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Date fechaNacimiento);
}
