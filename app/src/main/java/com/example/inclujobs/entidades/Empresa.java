package com.example.inclujobs.entidades;

public class Empresa {

    private int Id;
    private Usuario UsuarioDuenio;
    private String NombreComercial;
    private String RazonSocial;
    private String Cuit;
    private Sector Sector;
    private String Direccion;
    private String Descripcion;
    private Ciudad Ciudad;

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


    @Override
    public String toString() {
        return "Empresa{" +
                "Id=" + Id +
                ", UsuarioDuenio=" + UsuarioDuenio +
                ", NombreComercial='" + NombreComercial + '\'' +
                ", RazonSocial='" + RazonSocial + '\'' +
                ", Cuit='" + Cuit + '\'' +
                ", Sector=" + Sector +
                ", Direccion='" + Direccion + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Ciudad=" + Ciudad +
                '}';
    }
}
