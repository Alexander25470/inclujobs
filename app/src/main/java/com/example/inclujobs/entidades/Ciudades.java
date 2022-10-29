package com.example.inclujobs.entidades;

public class Ciudades {
    public int getIdCiudad() {
        return IdCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        IdCiudad = idCiudad;
    }

    public Provincias getProvincia() {
        return Provincia;
    }

    public void setProvincia(Provincias provincia) {
        Provincia = provincia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    int IdCiudad;
    Provincias Provincia;
    String Nombre;
}
