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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataListadoEmpresas extends AsyncTask<String, Void, String> {
    private static ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();
    private ICallBack callBack;
    private boolean filtro;
    private String nombreEmpresa, lugar, sector;

    public DataListadoEmpresas(ICallBack callBack, boolean fil, String nombre, String lug, String sec){
        this.callBack = callBack;
        this.filtro = fil;
        this.nombreEmpresa = nombre;
        this.lugar = lug;
        this.sector = sec;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "";

            if(filtro){
                query = "SELECT emp.*, ciu.Nombre NombreCiudad, prov.Id IdProvincia, prov.Nombre NombreProvincia FROM Empresas emp INNER JOIN Ciudades ciu ON emp.IdCiudad = ciu.Id INNER JOIN Provincias prov ON ciu.IdProvincia = prov.Id INNER JOIN Sectores sec ON emp.IdSector = sec.Id " +
                        "WHERE emp.RazonSocial LIKE '%" + nombreEmpresa + "%' OR emp.NombreComercial LIKE '%" + nombreEmpresa + "%' OR ciu.Nombre LIKE '%" + lugar + "%' OR prov.Nombre LIKE '%" + lugar + "%' OR sec.Nombre LIKE '%" + sector + "%'"; // filtrado de empresas
            }else{
                query = "SELECT emp.*, ciu.Nombre NombreCiudad, prov.Id IdProvincia, prov.Nombre NombreProvincia FROM Empresas emp INNER JOIN Ciudades ciu ON emp.IdCiudad = ciu.Id INNER JOIN Provincias prov ON ciu.IdProvincia = prov.Id";
            }

            ResultSet rs = st.executeQuery(query);

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
                sector.setId(rs.getInt("IdCiudad"));
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
