package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.OfertaAdapter;
import com.example.inclujobs.conexion.DataListadoOfertas;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.UserHelper;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListadoOfertasActivity extends AppCompatActivity {
    private ListView lvOfertas;
    private ArrayList<Oferta> listaOfertas = new ArrayList<Oferta>();
    private TextView tvUsuarioTBLO; // ToolBar Listado Ofertas
    private Button btnPublicarOfertaTBLO;
    private Usuario user;
    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_PUBLICAR_OFERTA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ofertas);
        lvOfertas = findViewById(R.id.lvOfertas);
        tvUsuarioTBLO = findViewById(R.id.tvUsuarioTBLO); // ToolBar Listado Ofertas
        btnPublicarOfertaTBLO = findViewById(R.id.btnPublicarOfertaTBLO); // ToolBar Listado Ofertas
        user = UserHelper.getUser(this);

        validarBotonesToolBar();
        cargarOfertas();

        Context ctx = this;
        lvOfertas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Oferta oferta = (Oferta) lvOfertas.getItemAtPosition(i);
                Gson gson = new Gson();
                String ofertaJson = gson.toJson(oferta);

                Intent intent = new Intent(ctx, DetalleOfertaActivity.class);
                intent.putExtra("oferta", ofertaJson);
                startActivity(intent);
            }
        });
    }

    private void validarBotonesToolBar(){
        if(user == null){
            tvUsuarioTBLO.setText("");
            btnPublicarOfertaTBLO.setText("Iniciar sesión");
        } else {
            tvUsuarioTBLO.setText("Bienvenido: "+ user.getNombre() + " " + user.getNombre());
            if(user.getIdEmpresa() == null)
            {
                btnPublicarOfertaTBLO.setVisibility(View.GONE);
            } else {
                btnPublicarOfertaTBLO.setText("Publicar oferta");
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

    private void cargarOfertas(){
        Context ctx = this;
        DataListadoOfertas task = new DataListadoOfertas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaOfertas = (ArrayList<Oferta>)obj;
                OfertaAdapter adapter = new OfertaAdapter(ctx, listaOfertas);
                lvOfertas.setAdapter(adapter);
            }
        }, -1);
        task.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_LOGIN)
        {
            if(resultCode == LoginActivity.RESULT_OK)
            {
                user = UserHelper.getUser(this);
                validarBotonesToolBar();
            }
        } else if (requestCode == REQUEST_PUBLICAR_OFERTA)
        {
            cargarOfertas();
        }

    }
}