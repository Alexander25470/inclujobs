package com.example.inclujobs.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertOferta;
import com.example.inclujobs.conexion.DataObtenerTiposDiscapacidades;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.TipoDiscapacidad;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.UserHelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;


public class CrearOfertaActivity extends AppCompatActivity {
    public static final int RESULT_OK = 1;
    private Oferta oferta;
    private TextView tvTituloCrear, tvDescripcionCrear, tvSalarioCrear;
    private Usuario user;
    private Button CancelarOferta;
    private boolean [] discapacidadesSeleccionadas;
    private ArrayList<TipoDiscapacidad> discapacidades = new ArrayList<>();
    private TextView tvDiscapacidades;
    private ArrayList<Integer> indexDiscapacidadesSeleccionadas = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);
        oferta = new Oferta();
        user = UserHelper.getUser(this);
        tvTituloCrear = findViewById(R.id.tvTituloCrear);
        tvDescripcionCrear = findViewById(R.id.tvDescripcionCrear);
        tvSalarioCrear = findViewById(R.id.tvSalarioCrear);
        CancelarOferta = findViewById(R.id.btnCancelarCrear);
        tvDiscapacidades = findViewById(R.id.tvDiscapacidades);

        tvDiscapacidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirSelectorDiscapacidades();
            }
        });

        cargarDiscapacidades();
    }

    private void abrirSelectorDiscapacidades(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearOfertaActivity.this);
        builder.setTitle("Seleccionar discapacidades");
        builder.setCancelable(false);

        String[] arr = new String[discapacidades.size()];
        for(int i=0 ; i< discapacidades.size();i++){
            arr[i] = discapacidades.get(i).getNombre();
        }
        builder.setMultiChoiceItems(arr, discapacidadesSeleccionadas, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    indexDiscapacidadesSeleccionadas.add(i);
                } else {
                    indexDiscapacidadesSeleccionadas.remove((Integer) i);
                }
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String test = "";
                for (int j = 0; j < indexDiscapacidadesSeleccionadas.size(); j++) {
                    if(j != 0) test += " - ";
                    test += discapacidades.get(j).getNombre();
                }
                if(indexDiscapacidadesSeleccionadas.size() == 0){
                    test = "Seleccionar";
                }
                tvDiscapacidades.setText(test);
            }
        });
        builder.show();
    }

    private void cargarDiscapacidades(){
        DataObtenerTiposDiscapacidades task = new DataObtenerTiposDiscapacidades(new ICallBack() {
            @Override
            public void function(Object obj) {
                discapacidades = (ArrayList<TipoDiscapacidad>)obj;
                discapacidadesSeleccionadas = new boolean[discapacidades.size()];
            }
        });

        task.execute();
    }

    public void CancelarOferta(View v){
        finish();
    }
    public void crearOferta(View view){

        if( tvTituloCrear.getText().toString() == null || tvTituloCrear.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Titulo", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( tvDescripcionCrear.getText().toString() == null || tvDescripcionCrear.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar una Descripcion", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( tvSalarioCrear.getText().toString() == null || tvSalarioCrear.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Salario", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(indexDiscapacidadesSeleccionadas.size() == 0){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe seleccionar almenos una discapacidad", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        ArrayList<TipoDiscapacidad> discapacidadesSel = new ArrayList<>();
        for (int j = 0; j < indexDiscapacidadesSeleccionadas.size(); j++) {
            discapacidadesSel.add(discapacidades.get(j));
        }
        oferta.setDiscapacidades(discapacidadesSel);

        oferta.setTitulo(tvTituloCrear.getText().toString());
        oferta.setDescripcion(tvDescripcionCrear.getText().toString());
        oferta.setSalario((Float.parseFloat(tvSalarioCrear.getText().toString())));

        DataInsertOferta task = new DataInsertOferta(oferta, (int)user.getIdEmpresa(), new ICallBack() {
            @Override
            public void function(Object obj) {
                Toast toast = Toast.makeText(getApplicationContext(),"Se creo la oferta correctamente", Toast.LENGTH_SHORT);
                toast.show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        task.execute();

    }

}