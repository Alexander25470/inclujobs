package com.example.inclujobs.conexion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inclujobs.activitys.ModificarEmpresaActivity;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataUpdateOferta extends AsyncTask<String, Void, String> {
    private Oferta oferta;
    private ICallBack callBack;

    private int result;
    private static String result2;

    public DataUpdateOferta(Oferta ofe, ICallBack callBack){
        oferta = ofe;
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            String query = "UPDATE `Ofertas` SET Titulo = '%s', Descripcion = '%s', Salario = %s WHERE id = %s";
            query = String.format(query, oferta.getTitulo(), oferta.getDescripcion(), oferta.getSalario(), oferta.getId());

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
