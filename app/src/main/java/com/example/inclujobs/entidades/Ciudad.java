package com.example.inclujobs.entidades;

public class Ciudad {
    public int getIdCiudad() {
        return IdCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        IdCiudad = idCiudad;
    }

    public com.example.inclujobs.entidades.Provincia getProvincia() {
        return Provincia;
    }

    public void setProvincia(com.example.inclujobs.entidades.Provincia provincia) {
        Provincia = provincia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    int IdCiudad;
    com.example.inclujobs.entidades.Provincia Provincia;
    String Nombre;
}