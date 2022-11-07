package com.example.inclujobs.entidades;

public class Sector {
    int Id;
    String Nombre;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        return Id +" - " + Nombre;
    }
}
