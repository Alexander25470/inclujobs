package com.example.inclujobs.conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.inclujobs.entidades.TipoDiscapacidad;
import com.example.inclujobs.entidades.Usuarios;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataTipoDiscapacidad  extends AsyncTask<String, Void, String> {
    private Context context;

    private int result;
    private static String result2;
    private static ArrayList<TipoDiscapacidad> listaTiposDiscapacidades = new ArrayList<TipoDiscapacidad>();
    private ICallBack callBack;

    public DataTipoDiscapacidad(Context ctx, ICallBack callBack){
        context = ctx;
    }

    protected String doInBackground(String... urls) {
        String response = "";
        if(!listaTiposDiscapacidades.isEmpty()){
            return "Conexion exitosa";
        }
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT * FROM TiposDiscapacidades";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                TipoDiscapacidad tipoDiscapacidad = new TipoDiscapacidad();

                tipoDiscapacidad.setId(rs.getInt("id"));
                tipoDiscapacidad.setNombre(rs.getString("nombre"));

                listaTiposDiscapacidades.add(tipoDiscapacidad);
            }

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
        callBack.function(listaTiposDiscapacidades);
    }

}
