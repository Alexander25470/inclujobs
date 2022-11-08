package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.EmpresaAdapter;
import com.example.inclujobs.conexion.DataListadoEmpresas;
import com.example.inclujobs.entidades.Empresa;
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

import java.util.ArrayList;

public class ListadoEmpresasActivity extends AppCompatActivity {
    private ListView lvEmpresas;
    private ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();
    private TextView tvUsuarioTB, txtEmpresa, txtLugar, txtArea; // ToolBar Listado Ofertas
    private Button btnPublicarOfertaTB;
    private Usuario user;
    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_PUBLICAR_OFERTA = 2;

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

        user = UserHelper.getUser(this);

        validarBotonesToolBar();
        cargarEmpresas();
        Context ctx = this;
        lvEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Empresa empresa = (Empresa) lvEmpresas.getItemAtPosition(i);
                Gson gson = new Gson();
                String empresaJson = gson.toJson(empresa);

                Intent intent = new Intent(ctx, DetalleEmpresa.class);
                intent.putExtra("empresa", empresaJson);
                startActivity(intent);
            }
        });

    }

    private void validarBotonesToolBar(){
        if(user == null){
            tvUsuarioTB.setText("");
            btnPublicarOfertaTB.setText("Iniciar sesión");
        } else {
            tvUsuarioTB.setText("Bienvenido: "+ user.getNombre() + " " + user.getNombre());
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

    private void cargarEmpresas(){
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
        }, false, null, null, null);
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
        }, true, txtEmpresa.getText().toString(), txtLugar.getText().toString(), txtArea.getText().toString());
        task.execute();
    }
}