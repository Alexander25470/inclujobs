package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataUpdateOferta;
import com.example.inclujobs.entidades.Oferta;
import com.example.inclujobs.helpers.ICallBack;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class EditarOfertaActivity extends AppCompatActivity {
    private Oferta oferta;
    private TextView tvTituloModificar, tvDescripcionModificar, tvSalarioModificar;
    public static final int RESTULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_oferta);

        Intent intent = getIntent();
        Gson gson = new Gson();
        oferta = gson.fromJson(intent.getStringExtra("oferta"), Oferta.class);

        tvTituloModificar = findViewById(R.id.tvTituloModificar);
        tvDescripcionModificar = findViewById(R.id.tvDescripcionModificar);
        tvSalarioModificar = findViewById(R.id.tvSalarioModificar);

        tvTituloModificar.setText(oferta.getTitulo());
        tvDescripcionModificar.setText(oferta.getDescripcion());
        tvSalarioModificar.setText(oferta.getSalario().toString());

    }

    public void cancelar(View v){
        finish();
    }

    public void modificarOferta(View view){

        if( tvTituloModificar.getText().toString() == null || tvTituloModificar.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Titulo", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( tvDescripcionModificar.getText().toString() == null || tvDescripcionModificar.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar una Descripcion", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( tvSalarioModificar.getText().toString() == null || tvSalarioModificar.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Salario", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        oferta.setTitulo(tvTituloModificar.getText().toString());
        oferta.setDescripcion(tvDescripcionModificar.getText().toString());
        oferta.setSalario((Float.parseFloat(tvSalarioModificar.getText().toString())));

        Context ctx = this;
        DataUpdateOferta task = new DataUpdateOferta(oferta, new ICallBack() {
            @Override
            public void function(Object obj) {
                String mensaje = "";

                if((int)obj == 1){
                    mensaje = "Oferta actualizada";
                } else {
                    mensaje = "Error al actualizar Oferta";
                }

                Toast toast = Toast.makeText(ctx,mensaje, Toast.LENGTH_SHORT);
                toast.show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        task.execute();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}