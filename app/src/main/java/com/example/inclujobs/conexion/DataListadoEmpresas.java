package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataListadoEmpresas extends AsyncTask<String, Void, String> {
    private static ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();
    private ICallBack callBack;

    public DataListadoEmpresas(ICallBack callBack){
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        if(!listaEmpresas.isEmpty()){
            return "Conexion exitosa";
        }

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT * FROM Empresas";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setNombreComercial(rs.getString("NombreComercial"));
                empresa.setRazonSocial(rs.getString("NombreComercial"));
                empresa.setCuit(rs.getString("CUIT"));
                empresa.setDireccion(rs.getString("Direccion"));
                empresa.setDescripcion(rs.getString("Descripcion "));

                Ciudad ciudad = new Ciudad();
                ciudad.setId(rs.getInt("IdCiudad"));
                empresa.setCiudad(ciudad);

                Sector sector = new Sector();
                sector.setIdSector(rs.getInt("IdCiudad"));
                empresa.setSector(sector);

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
