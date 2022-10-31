package com.example.inclujobs.entidades;

public class Ciudad {
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    int Id;
    com.example.inclujobs.entidades.Provincia Provincia;
    String Nombre;
}
