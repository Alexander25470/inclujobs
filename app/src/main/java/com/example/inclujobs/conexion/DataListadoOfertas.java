package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class DataListadoOfertas extends AsyncTask<String, Void, String> {
    private static ArrayList<Oferta> listaOfertas = new ArrayList<Oferta>();
    private ICallBack callBack;

    public DataListadoOfertas(ICallBack callBack){
        this.callBack = callBack;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        if(!listaOfertas.isEmpty()){
            return "Conexion exitosa";
        }

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT * FROM Ofertas";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {

                Oferta oferta = new Oferta();

                oferta.setId(rs.getInt("id"));
                oferta.setTitulo(rs.getString("Titulo"));
                oferta.setSalario(rs.getFloat("Salario"));
                oferta.setDescripcion(rs.getString("Descripcion"));
                listaOfertas.add(oferta);
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
        callBack.function(listaOfertas);
    }
}
