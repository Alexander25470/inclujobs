package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataObtenerOferta extends AsyncTask<String, Void, String> {
    private static Oferta oferta;
    private ICallBack callBack;
    private int idOferta;

    public DataObtenerOferta(int idOferta, ICallBack callBack){
        this.callBack = callBack;
        this.idOferta = idOferta;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {
            oferta = new Oferta();

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT o.Id, o.Titulo, o.Salario, o.Descripcion, o.IdEmpresa, emp.NombreComercial, emp.IdUsuarioDuenio FROM " +
                    "Ofertas o INNER JOIN Empresas emp ON o.IdEmpresa = emp.Id INNER JOIN Ciudades ciu ON emp.IdCiudad = ciu.id " +
                    "INNER JOIN Provincias prov ON ciu.IdProvincia = prov.Id";
            query += " WHERE o.Id = "+ idOferta;


            ResultSet rs = st.executeQuery(query);

            if(rs.next()) {
                Oferta aux = new Oferta();
                Empresa empresa = new Empresa();
                Usuario usuario = new Usuario();

                empresa.setId(rs.getInt("idEmpresa"));
                empresa.setNombreComercial(rs.getString("NombreComercial"));

                aux.setId(rs.getInt("id"));
                aux.setTitulo(rs.getString("Titulo"));
                aux.setSalario(rs.getFloat("Salario"));
                aux.setDescripcion(rs.getString("Descripcion"));
                usuario.setIdUsuario(rs.getInt("IdUsuarioDuenio"));

                empresa.setUsuarioDuenio(usuario);
                aux.setEmpresa(empresa);

                oferta = aux;
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
        callBack.function(oferta);
    }
}
