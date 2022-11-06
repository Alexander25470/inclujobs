package com.example.inclujobs.conexion;

import android.os.AsyncTask;

import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class DataListadoOfertas extends AsyncTask<String, Void, String> {
    private static ArrayList<Oferta> listaOfertas = new ArrayList<Oferta>();
    private ICallBack callBack;
    private int idEmpresa;

    public DataListadoOfertas(ICallBack callBack, int idEmpresa){
        this.callBack = callBack;
        this.idEmpresa = idEmpresa;
    }

    protected String doInBackground(String... urls) {
        String response = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String query = "SELECT o.Id, o.Titulo, o.Salario, o.Descripcion, o.IdEmpresa, emp.NombreComercial, emp.IdUsuarioDuenio FROM Ofertas o INNER JOIN Empresas emp " +
                    "ON o.IdEmpresa = emp.Id";

            if(idEmpresa != -1){
                query += " WHERE IdEmpresa = " + idEmpresa;
            }

            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
                Oferta oferta = new Oferta();
                Empresa empresa = new Empresa();
                Usuario usuario = new Usuario();

                empresa.setId(rs.getInt("idEmpresa"));
                empresa.setNombreComercial(rs.getString("NombreComercial"));

                oferta.setId(rs.getInt("id"));
                oferta.setTitulo(rs.getString("Titulo"));
                oferta.setSalario(rs.getFloat("Salario"));
                oferta.setDescripcion(rs.getString("Descripcion"));
                usuario.setIdUsuario(rs.getInt("IdUsuarioDuenio"));

                empresa.setUsuarioDuenio(usuario);

                oferta.setEmpresa(empresa);


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
