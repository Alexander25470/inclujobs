package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataListadoEmpresas;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.helpers.ICallBack;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoEmpresasActivity extends AppCompatActivity {
    private ListView lvEmpresas;
    private ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_empresas);
        lvEmpresas = findViewById(R.id.lvEmpresas);
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

    private void cargarEmpresas(){
        Context ctx = this;
        DataListadoEmpresas task = new DataListadoEmpresas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaEmpresas = (ArrayList<Empresa>)obj;
                ArrayAdapter<Empresa> adapter = new ArrayAdapter<Empresa>(ctx, android.R.layout.simple_list_item_1,listaEmpresas);
                lvEmpresas.setAdapter(adapter);
            }
        });
        task.execute();
    }
}