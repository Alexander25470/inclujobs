package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Provincia;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataObtenerCiudades extends AsyncTask<String, Void, String> {
    private static ArrayList<Ciudad> listaCiudades = new ArrayList<Ciudad>();
    private Integer idProvincia;
    private ICallBack callBack;

    public DataObtenerCiudades(Integer idProvincia, ICallBack callBack){
        this.callBack = callBack;
        this.idProvincia = idProvincia;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        listaCiudades = new ArrayList<Ciudad>();

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT * FROM Ciudades";
            if(idProvincia != null){
                query += " WHERE IdProvincia = " + idProvincia;
            }
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                Ciudad ciudad = new Ciudad();

                ciudad.setId(rs.getInt("Id"));
                ciudad.setNombre(rs.getString("Nombre"));

                listaCiudades.add(ciudad);
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
        callBack.function(listaCiudades);
    }
}
