package com.example.inclujobs.DTOs;

public class CVListadoDTO {
    private int idUsuario;
    private int idOferta;
    private byte[] Archivo;
    private String NombreArchivo;
    private String NombreTipoDiscapacidad;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }

    public byte[] getArchivo() {
        return Archivo;
    }

    public void setArchivo(byte[] archivo) {
        Archivo = archivo;
    }

    public String getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        NombreArchivo = nombreArchivo;
    }

    public String getNombreTipoDiscapacidad() {
        return NombreTipoDiscapacidad;
    }

    public void setNombreTipoDiscapacidad(String nombreTipoDiscapacidad) {
        NombreTipoDiscapacidad = nombreTipoDiscapacidad;
    }
}
