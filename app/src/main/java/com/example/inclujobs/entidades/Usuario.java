package com.example.inclujobs.entidades;

public class Usuario {
    private int IdUsuario;
    private String Nombre;
    private String Apellido;
    private String Email;
    private String Contra;
    private String Telefono;
    private Integer IdEmpresa;
    private TipoDiscapacidad TipoDiscapacidad;

    public TipoDiscapacidad getTipoDiscapacidad() {
        return TipoDiscapacidad;
    }

    public void setTipoDiscapacidad(TipoDiscapacidad tipoDiscapacidad) {
        TipoDiscapacidad = tipoDiscapacidad;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }
    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }
    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getContra() {
        return Contra;
    }
    public void setContra(String Contra) {
        this.Contra = Contra;
    }

    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public Integer getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        IdEmpresa = idEmpresa;
    }
}
