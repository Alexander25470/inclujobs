package com.example.inclujobs.entidades;

public class Empresa {
    public int getId() {
        return Id;
    }

    public void setId(int idEmpresa) {
        Id = idEmpresa;
    }

    public Usuario getUsuarioDuenio() {
        return UsuarioDuenio;
    }

    public void setUsuarioDuenio(Usuario usuarioDuenio) {
        UsuarioDuenio = usuarioDuenio;
    }

    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        NombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public String getCuit() {
        return Cuit;
    }

    public void setCuit(String cuit) {
        Cuit = cuit;
    }

    public com.example.inclujobs.entidades.Sector getSector() {
        return Sector;
    }

    public void setSector(com.example.inclujobs.entidades.Sector sector) {
        Sector = sector;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public com.example.inclujobs.entidades.Ciudad getCiudad() {
        return Ciudad;
    }

    public void setCiudad(com.example.inclujobs.entidades.Ciudad ciudad) {
        Ciudad = ciudad;
    }

    int Id;
    Usuario UsuarioDuenio;
    String NombreComercial;
    String RazonSocial;
    String Cuit;
    com.example.inclujobs.entidades.Sector Sector;
    String Direccion;
    String Descripcion;
    com.example.inclujobs.entidades.Ciudad Ciudad;
}
