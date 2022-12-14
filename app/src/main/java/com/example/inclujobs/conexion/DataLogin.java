package com.example.inclujobs.conexion;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.entidades.TipoDiscapacidad;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataLogin extends AsyncTask<String, Void, String> {
    private static Usuario usuario;
    private ICallBack callBack;
    private String email;
    private String pass;

    public DataLogin(String email, String pass, ICallBack callBack){
        this.callBack = callBack;
        this.email = email;
        this.pass = pass;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);

            String query = "SELECT U.*, E.Id IdEmpresa FROM Usuarios U LEFT JOIN Empresas E ON U.Id = E.IdUsuarioDuenio WHERE Email = ? AND Contrasenia = ? ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1,email);
            ps.setString(2,pass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("Id"));
                usuario.setEmail(rs.getString("Email"));
                usuario.setNombre(rs.getString("Nombre"));
                usuario.setApellido(rs.getString("Apellido"));
                usuario.setContra(rs.getString("Contrasenia"));
                usuario.setTelefono(rs.getString("Telefono"));
                TipoDiscapacidad td = new TipoDiscapacidad();
                td.setId(rs.getInt("IdTipoDiscapacidad"));
                if(rs.getObject("IdEmpresa") != null){
                    usuario.setIdEmpresa(rs.getInt("IdEmpresa"));
                }


                usuario.setTipoDiscapacidad(td);
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
        callBack.function(usuario);
    }
}
