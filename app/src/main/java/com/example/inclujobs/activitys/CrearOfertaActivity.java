package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inclujobs.MainActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertOferta;
import com.example.inclujobs.conexion.DataUpdateOferta;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.UserHelper;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;


public class CrearOfertaActivity extends AppCompatActivity {
    public static final int RESULT_OK = 1;
    private Oferta oferta;
    private TextView tvTituloCrear, tvDescripcionCrear, tvSalarioCrear;
    private Usuario user;
    private Button CancelarOferta;
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