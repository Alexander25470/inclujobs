package com.example.inclujobs.DTOs;

import java.io.File;

public class CVDTO {
    private int idUsuario;
    private int idOferta;
    private byte[] Archivo;
    private String NombreArchivo;

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

    @Override
    public String toString() {
        return "CVDTO{" +
                "idUsuario=" + idUsuario +
                ", idOferta=" + idOferta +
                ", NombreArchivo='" + NombreArchivo + '\'' +
                '}';
    }
}
