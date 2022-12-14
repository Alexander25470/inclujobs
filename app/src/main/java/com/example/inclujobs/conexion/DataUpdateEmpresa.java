package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataUpdateEmpresa extends AsyncTask<String, Void, String> {
    private Empresa empresa;
    private ICallBack callback;

    private int result;
    private static String result2;

    public DataUpdateEmpresa(Empresa emp, ICallBack callback){
        empresa = emp;
        this.callback = callback;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            String query = "UPDATE `Empresas` SET NombreComercial = '%s', RazonSocial = '%s', Descripcion = '%s' WHERE id = %s";
            query = String.format(query, empresa.getNombreComercial(), empresa.getRazonSocial(), empresa.getDescripcion(), empresa.getId());

            result = st.executeUpdate(query);

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
        callback.function(result);
    }
}
