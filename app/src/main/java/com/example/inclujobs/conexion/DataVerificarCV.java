package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataVerificarCV extends AsyncTask<String, Void, String> {
    private ICallBack callBack;
    private int IdOferta;
    private int IdUsuario;
    private boolean verificacion;

    public DataVerificarCV(ICallBack callBack, int IdOf, int IdUs){
        this.callBack = callBack;
        this.IdOferta = IdOf;
        this.IdUsuario = IdUs;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT COUNT(*) AS CANTIDAD FROM CVs WHERE IdOferta = " + IdOferta + " AND IdUsuario = " + IdUsuario;

            ResultSet rs = st.executeQuery(query);
            int cantidad = 0;
            while(rs.next()) {
                cantidad = rs.getInt("CANTIDAD");

                if(cantidad > 0){
                    verificacion = true;
                }else{
                    verificacion = false;
                }
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
        callBack.function(verificacion);
    }
}
