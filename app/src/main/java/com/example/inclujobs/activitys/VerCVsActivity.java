package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.inclujobs.DTOs.CVListadoDTO;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.CVAdapter;
import com.example.inclujobs.adapters.OfertaAdapter;
import com.example.inclujobs.conexion.DataListadoCVs;
import com.example.inclujobs.conexion.DataListadoOfertas;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;

import java.util.ArrayList;

public class VerCVsActivity extends AppCompatActivity {
    private ArrayList<CVListadoDTO> listaCVs = new ArrayList<CVListadoDTO>();
    private ListView lvCVs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cvs);
        lvCVs = findViewById(R.id.lvCVs);
        cargarCVs();


    }

    private void cargarCVs(){
        Context ctx = this;
        DataListadoCVs task = new DataListadoCVs(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaCVs = (ArrayList<CVListadoDTO>)obj;
                CVAdapter adapter = new CVAdapter(ctx, listaCVs);
                //ArrayAdapter<CVListadoDTO> adapter = new ArrayAdapter<CVListadoDTO>(ctx, android.R.layout.simple_list_item_1,listaCVs);
                lvCVs.setAdapter(adapter);
            }
        });
        task.execute();
    }
}