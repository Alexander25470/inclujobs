package com.example.inclujobs.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.google.gson.Gson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class DetalleOfertaActivity extends AppCompatActivity {
    private TextView lblTituloOfertaDetalle, lblEmpresaOfertaDetalle, lblDescripcionOfertaDetalle, lblSalarioOfertaDetalle;
    private Button btnEditarOfertaDetalle, btnEliminarOfertaDetalle, btnVerCvOfertaDetalle, btnAdjuntarCvOfertaDetalle;
    private Oferta oferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_oferta);

        lblTituloOfertaDetalle = findViewById(R.id.lblTituloOfertaDetalle);
        lblEmpresaOfertaDetalle = findViewById(R.id.lblEmpresaOfertaDetalle);
        lblDescripcionOfertaDetalle = findViewById(R.id.lblDescripcionOfertaDetalle);
        lblSalarioOfertaDetalle = findViewById(R.id.lblSalarioOfertaDetalle);

        Intent intent = getIntent();
        Gson gson = new Gson();
        oferta = gson.fromJson(intent.getStringExtra("oferta"), Oferta.class);

        lblTituloOfertaDetalle.setText(oferta.getTitulo());
        lblEmpresaOfertaDetalle.setText(oferta.getEmpresa().getNombreComercial());
        lblDescripcionOfertaDetalle.setText(oferta.getDescripcion());
        lblSalarioOfertaDetalle.setText("Salario: $" + oferta.getSalario().toString());
    }

    public void modificarOferta(View v){
        Gson gson = new Gson();
        String ofertaJson = gson.toJson(oferta);

        Intent intent = new Intent(this, EditarOfertaActivity.class);
        intent.putExtra("oferta", ofertaJson);
        startActivity(intent);


    }

    public void adjuntarCV(View view){
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intentPDF , "Seleccionar Archivo"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                readFile(data.getData());
            }
        }
    }

    private void readFile(Uri uri){
        Bitmap bitmap = null;
        InputStream inputStream = null;

        try{
            FileInputStream fis = new FileInputStream (new File(uri.getPath()));
            bitmap = BitmapFactory.decodeStream(fis);
        }catch (Exception ex){
            Toast toast = Toast.makeText(this,ex.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}