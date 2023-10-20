package com.gestion.empleados.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eps")
public class Eps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @OneToMany(mappedBy = "eps")
    private List<HabitanteCalle> habitantesCalle = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<HabitanteCalle> getHabitantesCalle() {
        return habitantesCalle;
    }

    public void setHabitantesCalle(List<HabitanteCalle> habitantesCalle) {
        this.habitantesCalle = habitantesCalle;
    }
}
