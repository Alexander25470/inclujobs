package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Provincia;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataObtenerProvincias extends AsyncTask<String, Void, String> {
    private static ArrayList<Provincia> listaProvincias = new ArrayList<Provincia>();
    private ICallBack callBack;

    public DataObtenerProvincias(ICallBack callBack){
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        if(!listaProvincias.isEmpty()){
            return "Conexion exitosa";
        }

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT * FROM Provincias";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                Provincia provincia = new Provincia();

                provincia.setId(rs.getInt("id"));
                provincia.setNombre(rs.getString("nombre"));

                listaProvincias.add(provincia);
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
        callBack.function(listaProvincias);
    }
}
