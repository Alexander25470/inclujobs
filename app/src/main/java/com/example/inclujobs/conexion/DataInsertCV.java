package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.DTOs.CVDTO;
import com.example.inclujobs.entidades.CV;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataInsertCV extends AsyncTask<String, Void, String> {
    private CVDTO cv;
    private ICallBack callBack;

    private int result;
    private static String result2;

    public DataInsertCV(CVDTO cv, ICallBack callBack){
        cv = cv;
        callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "INSERT INTO `CVs`(`IdUsuario`, `IdOferta`, `Archivo`) VALUES ('%s','%s','%s','%s', '%s');";
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setInt(1, cv.getIdUsuario());
            ps.setInt(2, cv.getIdOferta());
            ps.setBytes(3, cv.getArchivo());


            result = ps.executeUpdate();

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
        callBack.function(result2);
    }

}
