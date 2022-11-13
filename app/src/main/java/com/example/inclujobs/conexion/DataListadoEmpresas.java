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
import java.sql.Statement;
import java.util.ArrayList;

public class DataListadoEmpresas extends AsyncTask<String, Void, String> {
    private static ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();
    private ICallBack callBack;
    private String nombreEmpresa, lugar, sector;

    public DataListadoEmpresas(ICallBack callBack, String nombre, String lug, String sec){
        this.callBack = callBack;
        this.nombreEmpresa = nombre;
        this.lugar = lug;
        this.sector = sec;
    }

    protected String doInBackground(String... urls) {
        String response = "";
        listaEmpresas = new ArrayList<Empresa>();
        try {

            if(nombreEmpresa == null){
                nombreEmpresa = "";
            }
            if(lugar == null){
                lugar = "";
            }
            if(sector == null){
                sector = "";
            }
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            String query = "SELECT emp.*, ciu.Nombre NombreCiudad, prov.Id IdProvincia, prov.Nombre NombreProvincia, sec.Nombre nombreSector FROM Empresas emp INNER JOIN " +
                    "Ciudades ciu ON emp.IdCiudad = ciu.Id INNER JOIN Provincias prov ON ciu.IdProvincia = prov.Id INNER JOIN Sectores sec ON emp.IdSector = sec.Id";

            if(!nombreEmpresa.isEmpty() || !lugar.isEmpty() || !sector.isEmpty() ){
                query += " WHERE";
                query += " ('"+nombreEmpresa+"' = '' OR emp.RazonSocial LIKE '%"+ nombreEmpresa + "%' OR emp.NombreComercial LIKE '%"+ nombreEmpresa + "%')";
                query += " AND ('"+lugar+"' = '' OR ciu.Nombre LIKE '%"+ lugar + "%' OR prov.Nombre LIKE '%"+ lugar + "%')";
                query += " AND ('"+sector+"' = '' OR sec.Nombre LIKE '%"+ sector + "%')";
            }

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setNombreComercial(rs.getString("NombreComercial"));
                empresa.setRazonSocial(rs.getString("NombreComercial"));
                empresa.setCuit(rs.getString("CUIT"));
                empresa.setDireccion(rs.getString("Direccion"));
                empresa.setDescripcion(rs.getString("Descripcion"));

                Ciudad ciudad = new Ciudad();
                ciudad.setId(rs.getInt("IdCiudad"));
                ciudad.setNombre(rs.getString("NombreCiudad"));

                Provincia provincia = new Provincia();
                provincia.setId(rs.getInt("IdProvincia"));
                provincia.setNombre(rs.getString("NombreProvincia"));

                ciudad.setProvincia(provincia);
                empresa.setCiudad(ciudad);

                Sector sector = new Sector();
                sector.setId(rs.getInt("IdSector"));
                sector.setNombre(rs.getString("nombreSector"));
                empresa.setSector(sector);

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("IdUsuarioDuenio"));

                listaEmpresas.add(empresa);
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
        callBack.function(listaEmpresas);
    }
}
