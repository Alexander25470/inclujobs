package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataListadoEmpresas;
import com.example.inclujobs.conexion.DataListadoOfertas;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoOfertasActivity extends AppCompatActivity {
    private ListView lvOfertas;
    private ArrayList<Oferta> listaOfertas = new ArrayList<Oferta>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ofertas);
        lvOfertas = findViewById(R.id.lvOfertas);
        cargarOfertas();


    }
    private void cargarOfertas(){
        Context ctx = this;
        DataListadoOfertas task = new DataListadoOfertas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaOfertas = (ArrayList<Oferta>)obj;
                ArrayAdapter<Oferta> adapter = new ArrayAdapter<Oferta>(ctx, android.R.layout.simple_list_item_1,listaOfertas);
                lvOfertas.setAdapter(adapter);
            }
        });
        task.execute();
    }

}