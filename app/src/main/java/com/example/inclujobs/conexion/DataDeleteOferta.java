package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataDeleteOferta extends AsyncTask<String, Void, String> {
    private ICallBack callBack;
    private int idOferta;

    private int result;
    private static String result2;

    public DataDeleteOferta(int idOferta, ICallBack callBack){
        this.idOferta = idOferta;
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            String query = "DELETE FROM `CVs` WHERE idOferta = %s";
            query = String.format(query, idOferta);

            result = st.executeUpdate(query);

            //if(result == 1){
                query = "DELETE FROM `Ofertas` WHERE id = %s";
                query = String.format(query, idOferta);

                result = st.executeUpdate(query);
            //}

            response = "Conexion exitosa";
        }
        catch(Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        callBack.function(result);
    }
}
