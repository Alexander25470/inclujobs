package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataListadoEmpresas;
import com.example.inclujobs.conexion.DataListadoOfertas;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetalleEmpresa extends AppCompatActivity {
    private TextView lblNombreEmpresaDetalle, lblDireccionEmpresaDetalle, lblDescripcionEmpresaDetalle;
    private ListView lvOfertas;
    private ArrayList<Oferta> listaOferta = new ArrayList<Oferta>();
    private Button btnEditarEmpresaDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_empresa);
        Context ctx = this;

        lvOfertas = findViewById(R.id.lvOfertasDetalleEmpresa);
        lblNombreEmpresaDetalle = findViewById(R.id.lblNombreEmpresaDetalle);
        lblDireccionEmpresaDetalle = findViewById(R.id.lblDireccionEmpresaDetalle);
        lblDescripcionEmpresaDetalle = findViewById(R.id.lblDescripcionEmpresaDetalle);

        Intent intent = getIntent();
        Gson gson = new Gson();
        Empresa empresa = gson.fromJson(intent.getStringExtra("empresa"), Empresa.class);

        lblNombreEmpresaDetalle.setText(empresa.getNombreComercial());
        lblDireccionEmpresaDetalle.setText(empresa.getDireccion());
        lblDescripcionEmpresaDetalle.setText(empresa.getDescripcion());

        cargarOfertas(empresa.getId());

        btnEditarEmpresaDetalle = findViewById(R.id.btnEditarEmpresaDetalle);
        btnEditarEmpresaDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String empresaJson = gson.toJson(empresa);

                Intent intent = new Intent(ctx, ModificarEmpresaActivity.class);
                intent.putExtra("empresa", empresaJson);
                startActivity(intent);
            }
        });
    }

    private void cargarOfertas(int idEmpresa){
        Context ctx = this;
        DataListadoOfertas task = new DataListadoOfertas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaOferta = (ArrayList<Oferta>)obj;
                ArrayAdapter<Oferta> adapter = new ArrayAdapter<Oferta>(ctx, android.R.layout.simple_list_item_1,listaOferta);
                lvOfertas.setAdapter(adapter);
            }
        }, idEmpresa);
        task.execute();
    }
}