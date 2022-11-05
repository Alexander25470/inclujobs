package com.example.inclujobs.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inclujobs.DTOs.CVDTO;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertCV;
import com.example.inclujobs.conexion.DataLogin;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.UserHelper;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DetalleOfertaActivity extends AppCompatActivity {
    private TextView lblTituloOfertaDetalle, lblEmpresaOfertaDetalle, lblDescripcionOfertaDetalle, lblSalarioOfertaDetalle;
    private Button btnEditar, btnEliminar, btnVerCvs, btnAdjuntarCv;
    private Oferta oferta;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_oferta);

        lblTituloOfertaDetalle = findViewById(R.id.lblTituloOfertaDetalle);
        lblEmpresaOfertaDetalle = findViewById(R.id.lblEmpresaOfertaDetalle);
        lblDescripcionOfertaDetalle = findViewById(R.id.lblDescripcionOfertaDetalle);
        lblSalarioOfertaDetalle = findViewById(R.id.lblSalarioOfertaDetalle);
        btnEditar = findViewById(R.id.btnEditarOfertaDetalle);
        btnEliminar = findViewById(R.id.btnEliminarOfertaDetalle);
        btnVerCvs = findViewById(R.id.btnVerCvOfertaDetalle);
        btnAdjuntarCv = findViewById(R.id.btnAdjuntarCvOfertaDetalle);


        Intent intent = getIntent();
        Gson gson = new Gson();
        oferta = gson.fromJson(intent.getStringExtra("oferta"), Oferta.class);

        lblTituloOfertaDetalle.setText(oferta.getTitulo());
        lblEmpresaOfertaDetalle.setText(oferta.getEmpresa().getNombreComercial());
        lblDescripcionOfertaDetalle.setText(oferta.getDescripcion());
        lblSalarioOfertaDetalle.setText("Salario: $" + oferta.getSalario().toString());

        usuario = UserHelper.getUser(this);

        if(usuario == null){
            btnEditar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            btnAdjuntarCv.setVisibility(View.GONE);
            btnVerCvs.setVisibility(View.GONE);
        } else if(usuario.getIdUsuario() != oferta.getEmpresa().getUsuarioDuenio().getIdUsuario())
        {
            btnEditar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            btnVerCvs.setVisibility(View.GONE);
        }

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

                try {
                    Uri returnUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(returnUri);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] bytes = getArrayFromInputStream(bis);

                    String nombreArchivo = getFileName(returnUri);

                    guardarCV(bytes, nombreArchivo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getFileName(Uri uri){
        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();

        return returnCursor.getString(nameIndex);
    }

    private void guardarCV(byte[] bytes, String nombreArchivo){
        CVDTO cv = new CVDTO();
        cv.setIdOferta(oferta.getId());
        cv.setIdUsuario(usuario.getIdUsuario());
        cv.setArchivo(bytes);
        cv.setNombreArchivo(nombreArchivo);
        Context ctx = this;
        DataInsertCV task = new DataInsertCV(cv, new ICallBack() {
            @Override
            public void function(Object obj) {
                Toast toast = Toast.makeText(ctx,"CV ENVIADO", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        task.execute();
    }


    private void readFile3(Uri uri){
        FileOutputStream os;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] bytes = getArrayFromInputStream(bis);

            os = openFileOutput("newFileName", Context.MODE_PRIVATE);
            try(BufferedOutputStream salida = new BufferedOutputStream(os)){
                salida.write(bytes);
                salida.flush();
            }

            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // https://stackoverflow.com/questions/52539316/generate-a-pdf-using-streams
    private static byte[] getArrayFromInputStream(InputStream inputStream) throws IOException {
        byte[] bytes;
        byte[] buffer = new byte[1024];
        try(BufferedInputStream is = new BufferedInputStream(inputStream)){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int length;
            while ((length = is.read(buffer)) > -1 ) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = bos.toByteArray();
        }
        return bytes;
    }

    private static void writeContent(byte[] content, String fileToWriteTo) throws IOException {
        FileOutputStream os = new FileOutputStream(fileToWriteTo);
        try(BufferedOutputStream salida = new BufferedOutputStream(os)){
            salida.write(content);
            salida.flush();
        }
    }
}