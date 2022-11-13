package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Provincia;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataObtenerEmpresa extends AsyncTask<String, Void, String> {
    private static Empresa empresa = new Empresa();
    private ICallBack callBack;
    private int idEmpresa;

    public DataObtenerEmpresa(int idEmpresa, ICallBack callBack){
        this.callBack = callBack;
        this.idEmpresa = idEmpresa;
    }

    protected String doInBackground(String... urls) {
        String response = "";
        empresa = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            String query = "SELECT emp.*, ciu.Nombre NombreCiudad, prov.Id IdProvincia, prov.Nombre NombreProvincia, sec.Nombre nombreSector FROM Empresas emp INNER JOIN " +
                    "Ciudades ciu ON emp.IdCiudad = ciu.Id INNER JOIN Provincias prov ON ciu.IdProvincia = prov.Id INNER JOIN Sectores sec ON emp.IdSector = sec.Id";
            query += " WHERE emp.Id = " + idEmpresa;


            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Empresa aux = new Empresa();

                aux.setId(rs.getInt("id"));
                aux.setNombreComercial(rs.getString("NombreComercial"));
                aux.setRazonSocial(rs.getString("NombreComercial"));
                aux.setCuit(rs.getString("CUIT"));
                aux.setDireccion(rs.getString("Direccion"));
                aux.setDescripcion(rs.getString("Descripcion"));

                Ciudad ciudad = new Ciudad();
                ciudad.setId(rs.getInt("IdCiudad"));
                ciudad.setNombre(rs.getString("NombreCiudad"));

                Provincia provincia = new Provincia();
                provincia.setId(rs.getInt("IdProvincia"));
                provincia.setNombre(rs.getString("NombreProvincia"));

                ciudad.setProvincia(provincia);
                aux.setCiudad(ciudad);

                Sector sector = new Sector();
                sector.setId(rs.getInt("IdSector"));
                sector.setNombre(rs.getString("nombreSector"));
                aux.setSector(sector);

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("IdUsuarioDuenio"));

                empresa = aux;
            }

            response = "Conexion exitosa";
        }
        catch(Exception e) {
            e.printStackTrace();
            response = "Conexion no exitosa";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        callBack.function(empresa);
    }
}
