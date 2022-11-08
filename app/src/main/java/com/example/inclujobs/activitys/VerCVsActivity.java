package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.inclujobs.DTOs.CVListadoDTO;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.CVAdapter;
import com.example.inclujobs.adapters.OfertaAdapter;
import com.example.inclujobs.conexion.DataListadoCVs;
import com.example.inclujobs.conexion.DataListadoOfertas;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class VerCVsActivity extends AppCompatActivity {
    private ArrayList<CVListadoDTO> listaCVs = new ArrayList<CVListadoDTO>();
    private ListView lvCVs;
    private int idOferta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cvs);
        lvCVs = findViewById(R.id.lvCVs);

        Intent intent = getIntent();
        idOferta = intent.getIntExtra("IdOferta", 0);

        cargarCVs();

        lvCVs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CVListadoDTO cv = (CVListadoDTO) lvCVs.getItemAtPosition(i);
                try {
                    saveFile(cv.getArchivo(), cv.getNombreArchivo());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast toast = Toast.makeText(getApplicationContext(),"Descargando CV: " + cv.getNombreArchivo(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }

    private void cargarCVs(){
        Context ctx = this;
        DataListadoCVs task = new DataListadoCVs(idOferta, new ICallBack() {
            @Override
            public void function(Object obj) {
                listaCVs = (ArrayList<CVListadoDTO>)obj;
                CVAdapter adapter = new CVAdapter(ctx, listaCVs);
                lvCVs.setAdapter(adapter);
            }
        });
        task.execute();
    }
    private void saveFile(byte[] content, String fileName) throws IOException {
        //File file = new File(Environment.getExternalStorageDirectory() , fileName);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) , fileName);


        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.close();
        }catch (IOException e){
            Toast.makeText(this,"Error al descargar CV", Toast.LENGTH_SHORT).show();
        }

    }

}