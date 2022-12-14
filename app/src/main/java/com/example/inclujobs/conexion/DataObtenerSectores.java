package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataObtenerSectores extends AsyncTask<String, Void, String> {
    private static ArrayList<Sector> listaSectores = new ArrayList<Sector>();
    private ICallBack callBack;

    public DataObtenerSectores(ICallBack callBack){
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        if(!listaSectores.isEmpty()){
            return "Conexion exitosa";
        }

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT * FROM Sectores";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                Sector sector = new Sector();

                sector.setId(rs.getInt("id"));
                sector.setNombre(rs.getString("nombre"));

                listaSectores.add(sector);
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
        callBack.function(listaSectores);
    }
}
