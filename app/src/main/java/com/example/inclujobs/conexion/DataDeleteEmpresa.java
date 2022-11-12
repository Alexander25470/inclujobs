package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataDeleteEmpresa extends AsyncTask<String, Void, String> {
    private Empresa empresa;
    private ICallBack callBack;

    private int result;
    private static String result2;

    public DataDeleteEmpresa(Empresa emp, ICallBack cbk){
        empresa = emp;
        callBack = cbk;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            String query = "DELETE FROM `CVs` WHERE IdOferta IN (SELECT B.Id AS IdOferta FROM `Empresas` A INNER JOIN `Ofertas` B ON A.Id = B.IdEmpresa WHERE A.Id = %s)";
            query = String.format(query, empresa.getId());

            result = st.executeUpdate(query);

            //if(result == 1){
                query = "DELETE FROM `Ofertas` WHERE idEmpresa = %s";
                query = String.format(query, empresa.getId());

                result = st.executeUpdate(query);

                //if(result == 1){
                    query = "DELETE FROM `Empresas` WHERE id = %s";
                    query = String.format(query, empresa.getId());

                    result = st.executeUpdate(query);
                //}
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
