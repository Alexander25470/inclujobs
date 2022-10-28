package com.example.inclujobs.entidades;

public class Usuarios {
    int IdUsuario;
    String Nombre;
    String Apellido;
    String Email;
    String Contra;
    String Telefono;
    com.example.inclujobs.entidades.TipoDiscapacidad TipoDiscapacidad;

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
}
