package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.DTOs.CVDTO;
import com.example.inclujobs.DTOs.CVListadoDTO;
import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Provincia;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataListadoCVs extends AsyncTask<String, Void, String> {

    private static ArrayList<CVListadoDTO> CVs = new ArrayList<CVListadoDTO>();
    private ICallBack callBack;
    private int idOferta;

    public DataListadoCVs(int idOferta, ICallBack callBack){
        this.callBack = callBack;
        this.idOferta = idOferta;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            CVs = new ArrayList<CVListadoDTO>();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            String query = "SELECT cv.*, td.Nombre NombreTipoDiscapacidad FROM CVs cv INNER JOIN " +
                    "Usuarios usu ON cv.IdUsuario = usu.Id LEFT JOIN" +
                    " TiposDiscapacidades td on usu.IdTipoDiscapacidad = td.id " +
                    "WHERE cv.IdOferta = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idOferta);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                CVListadoDTO cv = new CVListadoDTO();

                cv.setIdUsuario(rs.getInt("IdUsuario"));
                cv.setIdOferta(rs.getInt("IdOferta"));
                cv.setArchivo(rs.getBytes("Archivo"));
                cv.setNombreArchivo(rs.getString("NombreArchivo"));
                cv.setNombreTipoDiscapacidad(rs.getString("NombreTipoDiscapacidad"));

                CVs.add(cv);
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
        callBack.function(CVs);
    }
}
