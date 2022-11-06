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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataListadoCVs extends AsyncTask<String, Void, String> {

    private static ArrayList<CVListadoDTO> CVs = new ArrayList<CVListadoDTO>();
    private ICallBack callBack;

    public DataListadoCVs(ICallBack callBack){
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT cv.*, td.Nombre NombreTipoDiscapacidad FROM CVs cv INNER JOIN Usuarios usu ON cv.IdUsuario = usu.Id INNER JOIN TiposDiscapacidades td on usu.IdTipoDiscapacidad = td.id";
            ResultSet rs = st.executeQuery(query);

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
