package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.DTOs.CVDTO;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataInsertOferta extends AsyncTask<String, Void, String> {

    private Oferta oferta;
    private ICallBack callBack;
    private int idEmpresa;

    private int result;
    private static String result2;

    public DataInsertOferta(Oferta oferta, int idEmpresa, ICallBack callBack){
        this.oferta = oferta;
        this.callBack = callBack;
        this.idEmpresa = idEmpresa;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            String query = "INSERT INTO `Ofertas`(`idEmpresa`, `Titulo`, `Descripcion`, `Salario`) VALUES (?,?,?,?);";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, idEmpresa);
            ps.setString(2,  oferta.getTitulo());
            ps.setString(3, oferta.getDescripcion());
            ps.setFloat(4, oferta.getSalario());

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
