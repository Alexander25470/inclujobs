package com.example.inclujobs.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inclujobs.DTOs.CVDTO;
import com.example.inclujobs.R;
import com.example.inclujobs.adapters.OfertaAdapter;
import com.example.inclujobs.conexion.DataDeleteOferta;
import com.example.inclujobs.conexion.DataInsertCV;
import com.example.inclujobs.conexion.DataInsertOferta;
import com.example.inclujobs.conexion.DataListadoOfertas;
import com.example.inclujobs.conexion.DataObtenerOferta;
import com.example.inclujobs.conexion.DataVerificarCV;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.UserHelper;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetalleOfertaActivity extends AppCompatActivity {
    private TextView lblTituloOfertaDetalle, lblEmpresaOfertaDetalle, lblDescripcionOfertaDetalle, lblSalarioOfertaDetalle;
    private Button btnEditar, btnEliminar, btnVerCvs, btnAdjuntarCv;
    private Oferta oferta;
    private Usuario user;
    private TextView tvUsuarioTB; // ToolBar Listado Ofertas
    private Button btnPublicarOfertaTB;
    private int idOferta;
    private final int REQUEST_LOGIN = 1;
    private final int REQUEST_PUBLICAR_OFERTA = 2;
    private final int REQUEST_ADJUNTAR_CV = 3;
    private final int REQUEST_MODIFICAR_OFERTA = 4;
    public static final int RESULT_ACTUALIZAR_LISTADO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_oferta);
        user = UserHelper.getUser(this);

        lblTituloOfertaDetalle = findViewById(R.id.lblTituloOfertaDetalle);
        lblEmpresaOfertaDetalle = findViewById(R.id.lblEmpresaOfertaDetalle);
        lblDescripcionOfertaDetalle = findViewById(R.id.lblDescripcionOfertaDetalle);
        lblSalarioOfertaDetalle = findViewById(R.id.lblSalarioOfertaDetalle);
        btnEditar = findViewById(R.id.btnEditarOfertaDetalle);
        btnEliminar = findViewById(R.id.btnEliminarOfertaDetalle);
        btnVerCvs = findViewById(R.id.btnVerCvOfertaDetalle);
        btnAdjuntarCv = findViewById(R.id.btnAdjuntarCvOfertaDetalle);
        tvUsuarioTB = findViewById(R.id.tvUsuarioTB); // ToolBar Listado
        btnPublicarOfertaTB = findViewById(R.id.btnPublicarOfertaTB); // ToolBar


        validarBotonesToolBar();

        Intent intent = getIntent();
        Gson gson = new Gson();
        oferta = gson.fromJson(intent.getStringExtra("oferta"), Oferta.class);
        idOferta = oferta.getId();

        cargarOferta();

        user = UserHelper.getUser(this);

        if(user == null){
            btnEditar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);
            btnAdjuntarCv.setVisibility(View.GONE);
            btnVerCvs.setVisibility(View.GONE);
        } else {
            if(user.getIdUsuario() != oferta.getEmpresa().getUsuarioDuenio().getIdUsuario())
            {
                btnEditar.setVisibility(View.GONE);
                btnEliminar.setVisibility(View.GONE);
                btnVerCvs.setVisibility(View.GONE);
            }
            if(user.getIdEmpresa() != null){
                btnAdjuntarCv.setVisibility(View.GONE);
            }
        }

        if(user != null){
            verificarCvEnviado(oferta.getId());
        }
    }

    public void modificarOferta(View v){
        Gson gson = new Gson();
        String ofertaJson = gson.toJson(oferta);

        Intent intent = new Intent(this, EditarOfertaActivity.class);
        intent.putExtra("oferta", ofertaJson);
        startActivityForResult(intent,REQUEST_MODIFICAR_OFERTA);

    }

    private void obtenerOferta(){
        DataObtenerOferta task = new DataObtenerOferta(idOferta, new ICallBack() {
            @Override
            public void function(Object obj) {
                oferta = (Oferta)obj;
                cargarOferta();
            }
        });
        task.execute();
    }

    private void cargarOferta(){
        lblTituloOfertaDetalle.setText(oferta.getTitulo());
        lblEmpresaOfertaDetalle.setText(oferta.getEmpresa().getNombreComercial());
        lblDescripcionOfertaDetalle.setText(oferta.getDescripcion());
        lblSalarioOfertaDetalle.setText("Salario: $" + oferta.getSalario().toString());
    }

    public void verCVs(View v){
        Intent intent = new Intent(this, VerCVsActivity.class);
        intent.putExtra("IdOferta", oferta.getId());
        startActivity(intent);
    }

    public void adjuntarCV(View view){
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intentPDF , "Seleccionar Archivo"), REQUEST_ADJUNTAR_CV);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ADJUNTAR_CV){
            if(resultCode == RESULT_OK){

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
        } else if(requestCode == REQUEST_MODIFICAR_OFERTA){
            obtenerOferta();
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
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
        cv.setIdUsuario(user.getIdUsuario());
        cv.setArchivo(bytes);
        cv.setNombreArchivo(nombreArchivo);
        Context ctx = this;
        DataInsertCV task = new DataInsertCV(cv, new ICallBack() {
            @Override
            public void function(Object obj) {
                btnAdjuntarCv.setEnabled(false);
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

    private void validarBotonesToolBar(){
        if(user == null){
            tvUsuarioTB.setText("");
            btnPublicarOfertaTB.setText("Iniciar sesión");
        } else {
            tvUsuarioTB.setText("Bienvenido: "+ user.getNombre() + " " + user.getApellido());
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

    private void verificarCvEnviado(int IdOferta){
        int idUsuario = user.getIdUsuario();
        DataVerificarCV task = new DataVerificarCV(new ICallBack() {
            @Override
            public void function(Object obj) {
                boolean verificacion = (boolean)obj;
                if(verificacion){
                    btnAdjuntarCv.setEnabled(false);
                }else{
                    btnAdjuntarCv.setEnabled(true);
                }
            }
        }, IdOferta, idUsuario);
        task.execute();
    }

    public void eliminarOferta(View v){
        Context ctx = this;
        DataDeleteOferta task = new DataDeleteOferta(oferta.getId(), new ICallBack() {
            @Override
            public void function(Object obj) {
                if((int)obj == 1){
                    Toast toast = Toast.makeText(ctx,"Oferta eliminada", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_ACTUALIZAR_LISTADO, returnIntent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(ctx,"Error al eliminar Oferta", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        task.execute();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_ACTUALIZAR_LISTADO, returnIntent);
        finish();
    }

}