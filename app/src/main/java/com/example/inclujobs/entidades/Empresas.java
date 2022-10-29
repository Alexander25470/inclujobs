package com.example.inclujobs.entidades;

public class Empresas {
    public int getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public Usuarios getUsuarioDuenio() {
        return UsuarioDuenio;
    }

    public void setUsuarioDuenio(Usuarios usuarioDuenio) {
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

    public Sectores getSector() {
        return Sector;
    }

    public void setSector(Sectores sector) {
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

    public Ciudades getCiudad() {
        return Ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        Ciudad = ciudad;
    }

    int IdEmpresa;
    Usuarios UsuarioDuenio;
    String NombreComercial;
    String RazonSocial;
    String Cuit;
    Sectores Sector;
    String Direccion;
    String Descripcion;
    Ciudades Ciudad;
}
