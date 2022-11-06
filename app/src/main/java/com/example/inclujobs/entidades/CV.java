package com.example.inclujobs.entidades;

public class CV {
    private Usuario Usuario;
    private Oferta Oferta;
    private Archivo Archivo;
    private String NombreArchivo;

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario usuario) {
        Usuario = usuario;
    }

    public com.example.inclujobs.entidades.Oferta getOferta() {
        return Oferta;
    }

    public void setOferta(Oferta oferta) {
        Oferta = oferta;
    }

    public Archivo getArchivo() {
        return Archivo;
    }

    public void setArchivo(Archivo archivo) {
        Archivo = archivo;
    }

    public String getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        NombreArchivo = nombreArchivo;
    }
}
