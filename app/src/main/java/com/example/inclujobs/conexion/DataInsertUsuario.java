package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataInsertUsuario extends AsyncTask<String, Void, String> {
    private Usuario usuario;
    private ICallBack callBack;

    private int result;
    private static String result2;

    public DataInsertUsuario(Usuario usr, ICallBack callBack){
        usuario = usr;
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "INSERT INTO `Usuarios`(`Nombre`, `Apellido`, `Email`, `Contrasenia`, `Telefono`, `IdTipoDiscapacidad`) VALUES ('%s','%s','%s','%s', '%s', %s)";
            query = String.format(query, usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getContra(),
            usuario.getTelefono(), usuario.getTipoDiscapacidad().getId());
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
        callBack.function(result);
    }
}
