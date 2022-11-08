package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.Empresa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataInsertEmpresa extends AsyncTask<String, Void, String> {
    private Empresa empresa;
    private Context context;

    private int result;
    private static String result2;

    public DataInsertEmpresa(Empresa emp, Context ctx){
        empresa = emp;
        context = ctx;
    }

    protected String doInBackground(String... urls) {
        String response = "";
        int idUsuario = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "INSERT INTO `Usuarios`(`Nombre`, `Apellido`, `Email`, `Contrasenia`, `Telefono`) VALUES ('%s','%s','%s','%s', '%s');";
            query = String.format(query, empresa.getUsuarioDuenio().getNombre(), empresa.getUsuarioDuenio().getApellido(), empresa.getUsuarioDuenio().getEmail(), empresa.getUsuarioDuenio().getContra(),
                    empresa.getUsuarioDuenio().getTelefono());
            result = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();

            if(rs.next()) {
                idUsuario = rs.getInt(1);
            }

            query = "";
            query = "INSERT INTO `Empresas`(`IdUsuarioDuenio`, `NombreComercial`, `RazonSocial`, `CUIT`, `IdSector`, `Direccion`, `Descripcion`, `IdCiudad`) VALUES (%s,'%s','%s','%s', %s, '%s', '%s', %s)";
            query = String.format(query, idUsuario, empresa.getNombreComercial(), empresa.getRazonSocial(), empresa.getCuit(),
                    empresa.getSector().getId(), empresa.getDireccion(), empresa.getDescripcion(), empresa.getCiudad().getId());
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
            mensaje = "Empresa registrada";
        } else {
            mensaje = "Error al registrar Empresa";
        }
        Toast toast = Toast.makeText(context,mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }
}
