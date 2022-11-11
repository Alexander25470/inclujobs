package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.EmpresaAdapter;
import com.example.inclujobs.conexion.DataListadoEmpresas;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.UserHelper;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoEmpresasActivity extends AppCompatActivity {
    private ListView lvEmpresas;
    private ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();
    private TextView tvUsuarioTB, txtEmpresa, txtLugar, txtArea; // ToolBar Listado Ofertas
    private Button btnPublicarOfertaTB;
    private Usuario user;
    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_PUBLICAR_OFERTA = 2;
    private final int REQUEST_DETALLE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_empresas);
        lvEmpresas = findViewById(R.id.lvEmpresas);
        tvUsuarioTB = findViewById(R.id.tvUsuarioTB); // ToolBar Listado Ofertas
        txtEmpresa = findViewById(R.id.txtNombreEmpresaFiltro);
        txtLugar = findViewById(R.id.txtLugarFiltro);
        txtArea = findViewById(R.id.txtAreaFiltro);
        btnPublicarOfertaTB = findViewById(R.id.btnPublicarOfertaTB); // ToolBar Listado Ofertas

        Intent intent = getIntent();
        String lugarInicial = intent.getStringExtra("lugar");
        txtLugar.setText(lugarInicial);

        user = UserHelper.getUser(this);
        validarBotonesToolBar();
        cargarEmpresas(lugarInicial);
        Context ctx = this;
        lvEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Empresa empresa = (Empresa) lvEmpresas.getItemAtPosition(i);
                Gson gson = new Gson();
                String empresaJson = gson.toJson(empresa);

                Intent intent = new Intent(ctx, DetalleEmpresa.class);
                intent.putExtra("empresa", empresaJson);
                startActivityForResult(intent, REQUEST_DETALLE);
            }
        });

    }

    private void validarBotonesToolBar(){
        if(user == null){
            tvUsuarioTB.setText("");
            btnPublicarOfertaTB.setText("Iniciar sesión");
        }

        else {
            tvUsuarioTB.setText("Bienvenido: "+ user.getNombre() + " " + user.getApellido());
            if(user.getIdEmpresa() == null)
            {
                btnPublicarOfertaTB.setVisibility(View.GONE);
            } else {
                btnPublicarOfertaTB.setText("Publicar oferta");
            }
        }
    }

    public void clickPublicarOfertaOLogin(View v){
        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        } else {
            Intent intent = new Intent(this, CrearOfertaActivity.class);
            startActivityForResult(intent, REQUEST_PUBLICAR_OFERTA);
        }
    }

    private void cargarEmpresas(String lugar){
        Context ctx = this;
        DataListadoEmpresas task = new DataListadoEmpresas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaEmpresas = (ArrayList<Empresa>)obj;
                //ArrayAdapter<Empresa> adapter = new ArrayAdapter<Empresa>(ctx, android.R.layout.simple_list_item_1,listaEmpresas);
                //OfertaAdapter adapter = new OfertaAdapter(ctx, listaOfertas);
                EmpresaAdapter adapter = new EmpresaAdapter(ctx, listaEmpresas);
                lvEmpresas.setAdapter(adapter);
            }
        }, null, lugar, null);
        task.execute();
    }

    public void filtrarEmpresas(View v){
        Context ctx = this;
        DataListadoEmpresas task = new DataListadoEmpresas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaEmpresas = (ArrayList<Empresa>)obj;
                EmpresaAdapter adapter = new EmpresaAdapter(ctx, listaEmpresas);
                lvEmpresas.setAdapter(adapter);
            }
        }, txtEmpresa.getText().toString(), txtLugar.getText().toString(), txtArea.getText().toString());
        task.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if(requestCode == REQUEST_LOGIN)
        //{
        //if(resultCode == LoginActivity.RESULT_OK)
        //{
        user = UserHelper.getUser(this); // porque puede iniciar sesion desde el detalle
        validarBotonesToolBar();
        //}
        //}
        if (requestCode == REQUEST_PUBLICAR_OFERTA || (requestCode == REQUEST_DETALLE && resultCode == DetalleEmpresa.RESULT_ACTUALIZAR_LISTADO) )
        {
            Intent intent = getIntent();
            String lugarInicial = intent.getStringExtra("lugar");
            txtLugar.setText(lugarInicial);
            cargarEmpresas(lugarInicial);
        }

    }
}