package com.example.inclujobs.entidades;

public class Oferta {
    private Empresa Empresa;
    private int Id;
    private String Titulo;
    private Float Salario;
    private String Descripcion;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public com.example.inclujobs.entidades.Empresa getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(com.example.inclujobs.entidades.Empresa empresa) {
        Empresa = empresa;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public Float getSalario() {
        return Salario;
    }

    public void setSalario(Float salario) {
        Salario = salario;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "Empresa=" + Empresa +
                ", Id=" + Id +
                ", Titulo='" + Titulo + '\'' +
                ", Salario=" + Salario +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }
}
