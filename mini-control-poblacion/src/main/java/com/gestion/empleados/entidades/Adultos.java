package com.gestion.empleados.entidades;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Entity
@Table(name = "adultos")
public class Adultos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String primerNombre;

    private String segundoNombre;

    @NotEmpty
    private String primerApellido;

    @NotEmpty
    private String segundoApellido;

    private String consecutivo;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @NotEmpty
    private String sexo;

    @NotEmpty
    private String tipoDocumento;

    private String tipoDocumentoActual;

    private String numeroDocumentoActual;

    private boolean alerta;

    @JoinColumn(name = "eps_id")
    @ManyToOne
    private Eps eps;

    @JoinColumn(name = "entidad_id")
    @ManyToOne
    private Entidades entidades;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaRegistro;

    private String nombreFuncionario;

    private String centroProteccion;

    public Adultos() { super(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getConsecutivo() {return consecutivo;}

    public void setConsecutivo(String consecutivo) {this.consecutivo = consecutivo;}

    public String getTipoDocumento() {return tipoDocumento;}

    public void setTipoDocumento(String tipoDocumento) {this.tipoDocumento = tipoDocumento;}

    public String getTipoDocumentoActual() {return tipoDocumentoActual;}

    public void setTipoDocumentoActual(String tipoDocumentoActual) {this.tipoDocumentoActual = tipoDocumentoActual;}

    public String getNumeroDocumentoActual() {return numeroDocumentoActual;}

    public void setNumeroDocumentoActual(String numeroDocumentoActual) {this.numeroDocumentoActual = numeroDocumentoActual;}

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isAlerta() {return alerta;}

    public void setAlerta(boolean alerta) {this.alerta = alerta;}

    public Eps getEps() {
        return eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }

    public Entidades getEntidades() {
        return entidades;
    }

    public void setEntidades(Entidades entidades) {
        this.entidades = entidades;
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getCentroProteccion() {
        return centroProteccion;
    }

    public void setCentroProteccion(String centroProteccion) {
        this.centroProteccion = centroProteccion;
    }
}
