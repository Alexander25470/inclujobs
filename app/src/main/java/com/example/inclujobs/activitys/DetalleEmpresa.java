package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.OfertaAdapter;
import com.example.inclujobs.conexion.DataDeleteEmpresa;
import com.example.inclujobs.conexion.DataListadoOfertas;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;

public class DetalleEmpresa extends AppCompatActivity {
    private TextView lblNombreEmpresaDetalle, lblDireccionEmpresaDetalle, lblDescripcionEmpresaDetalle;
    private ListView lvOfertas;
    private ArrayList<Oferta> listaOfertas = new ArrayList<Oferta>();
    private Button btnEditarEmpresaDetalle, btnEliminarEmpresaDetalle;
    private Usuario user;
    private TextView tvUsuarioTB; // ToolBar Listado Ofertas
    private Button btnPublicarOfertaTB;
    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_PUBLICAR_OFERTA = 2;
    private final int REQUEST_MODIFICAR_EMPRESA = 3;
    public static final int RESULT_ACTUALIZAR_LISTADO = 2;
    private Empresa empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_empresa);
        Context ctx = this;

        lvOfertas = findViewById(R.id.lvOfertasDetalleEmpresa);
        lblNombreEmpresaDetalle = findViewById(R.id.lblTituloOfertaDetalle);
        lblDireccionEmpresaDetalle = findViewById(R.id.lblEmpresaOfertaDetalle);
        lblDescripcionEmpresaDetalle = findViewById(R.id.lblDescripcionOfertaDetalle);
        tvUsuarioTB = findViewById(R.id.tvUsuarioTB); // ToolBar Listado
        btnPublicarOfertaTB = findViewById(R.id.btnPublicarOfertaTB); // ToolBar

        Intent intent = getIntent();
        Gson gson = new Gson();
        empresa = gson.fromJson(intent.getStringExtra("empresa"), Empresa.class);

        user = UserHelper.getUser(this);
        validarBotonesToolBar();

        lblNombreEmpresaDetalle.setText(empresa.getNombreComercial());
        lblDireccionEmpresaDetalle.setText(empresa.getDireccion());
        lblDescripcionEmpresaDetalle.setText(empresa.getDescripcion());

        cargarOfertas(empresa.getId());

        btnEditarEmpresaDetalle = findViewById(R.id.btnEditarOfertaDetalle);
        btnEditarEmpresaDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String empresaJson = gson.toJson(empresa);

                Intent intent = new Intent(ctx, ModificarEmpresaActivity.class);
                intent.putExtra("empresa", empresaJson);
                startActivityForResult(intent, REQUEST_MODIFICAR_EMPRESA);
            }
        });

        btnEliminarEmpresaDetalle = findViewById(R.id.btnEliminarOfertaDetalle);
        btnEliminarEmpresaDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarEmpresa(empresa.getId());
            }
        });
    }

    private void cargarOfertas(int idEmpresa){
        Context ctx = this;
        DataListadoOfertas task = new DataListadoOfertas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaOfertas = (ArrayList<Oferta>)obj;
                OfertaAdapter adapter = new OfertaAdapter(ctx, listaOfertas);
                lvOfertas.setAdapter(adapter);
            }
        }, idEmpresa, null, "");
        task.execute();
    }

    private void eliminarEmpresa(int idEmpresa){
        Empresa empresa = new Empresa();

        empresa.setId(idEmpresa);
        Context ctx = this;
        DataDeleteEmpresa task = new DataDeleteEmpresa(empresa, new ICallBack() {
            @Override
            public void function(Object obj) {
                if((int)obj == 1){
                    Toast toast = Toast.makeText(ctx, "Empresa eliminada", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_ACTUALIZAR_LISTADO, returnIntent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(ctx,  "Error al eliminar Empresa", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        task.execute();
    }

    private void validarBotonesToolBar(){
        if(user == null){
            tvUsuarioTB.setText("");
            btnPublicarOfertaTB.setText("Iniciar sesión");
        } else {
            tvUsuarioTB.setText("Bienvenido: "+ user.getNombre()+ " " + user.getApellido());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PUBLICAR_OFERTA){
            cargarOfertas(empresa.getId());
        }
        Intent returnIntent = new Intent();
        setResult(RESULT_ACTUALIZAR_LISTADO, returnIntent);

    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_ACTUALIZAR_LISTADO, returnIntent);
        finish();
    }
}