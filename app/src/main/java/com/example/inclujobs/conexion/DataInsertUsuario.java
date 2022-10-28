package com.example.inclujobs.conexion;

import android.content.Context;
import android.widget.Toast;

import com.example.inclujobs.entidades.Usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataInsertUsuario {
    private Usuarios usuario;
    private Context context;

    private int result;
    private static String result2;

    public DataInsertUsuario(Usuarios usr, Context ctx){
        usuario = usr;
        context = ctx;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "INSERT INTO `Usuarios`(`Nombre`, `Apellido`, `Email`, `Contrasenia`, `Telefono`) VALUES ('%s','%s','%s','%s', '%s')";
            query = String.format(query, usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getContra(),
            usuario.getTelefono());
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
        String mensaje = "";
        if(result == 1){
            mensaje = "Usuario registrado";
        } else {
            mensaje = "Error al registrar Usuario";
        }
        Toast toast = Toast.makeText(context,mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }
}
