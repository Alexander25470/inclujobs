package com.example.inclujobs.entidades;

public class Ciudad {
    private Integer Id;
    private Provincia Provincia;
    private String Nombre;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Provincia getProvincia() {
        return Provincia;
    }

    public void setProvincia(Provincia provincia) {
        Provincia = provincia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        String str = "";
        if(Id == null){
            str = Nombre;
        } else {
            str = Id +" - " + Nombre;
        }
        if(Provincia != null){
            str += Provincia.getNombre();
        }
        return str;
    }
}
