package com.gestion.empleados.repositorios;

import com.gestion.empleados.entidades.Adultos;
import com.gestion.empleados.entidades.Indigenas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AdultosRepository extends PagingAndSortingRepository<Adultos, Long> {
    @Query("SELECT h.consecutivo FROM Adultos h ORDER BY h.consecutivo DESC")
    String obtenerUltimoConsecutivoDesdeBaseDeDatos();

    @Query("SELECT h FROM Adultos h WHERE " +
            "(:input IS NULL OR " +
            "h.primerNombre = :input OR " +
            "h.segundoNombre = :input OR " +
            "h.primerApellido = :input OR " +
            "h.segundoApellido = :input)")
    List<Adultos> findByAtributos(String input);
}
