package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertUsuario;
import com.example.inclujobs.conexion.DataUpdateEmpresa;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.TipoDiscapacidad;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarEmpresaActivity extends AppCompatActivity {
    private TextView txtNombreComercialModificarEmpresa, txtRazonSocialModificarEmpresa, txtDescripcionModificarEmpresa;
    private Button btnAceptarModificarEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ocultar action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_modificar_empresa);

        txtNombreComercialModificarEmpresa = findViewById(R.id.txtNombreComercialModificarEmpresa);
        txtRazonSocialModificarEmpresa = findViewById(R.id.txtRazonSocialModificarEmpresa);
        txtDescripcionModificarEmpresa = findViewById(R.id.txtDescripcionModificarEmpresa);

        Intent intent = getIntent();
        Gson gson = new Gson();
        Empresa empresa = gson.fromJson(intent.getStringExtra("empresa"), Empresa.class);

        txtNombreComercialModificarEmpresa.setText(empresa.getNombreComercial());
        txtRazonSocialModificarEmpresa.setText(empresa.getRazonSocial());
        txtDescripcionModificarEmpresa.setText(empresa.getDescripcion());

        btnAceptarModificarEmpresa = findViewById(R.id.btnAceptarModificarEmpresa);
        btnAceptarModificarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarEmpresa(empresa.getId());
            }
        });
    }

    private void modificarEmpresa(int idEmpresa){
        Empresa empresa = new Empresa();

        if( txtNombreComercialModificarEmpresa.getText().toString() == null || txtNombreComercialModificarEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Nombre comercial", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtRazonSocialModificarEmpresa.getText().toString() == null || txtRazonSocialModificarEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar una Razón social", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtDescripcionModificarEmpresa.getText().toString() == null || txtDescripcionModificarEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar una Descripción", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        empresa.setNombreComercial(txtNombreComercialModificarEmpresa.getText().toString());
        empresa.setRazonSocial(txtRazonSocialModificarEmpresa.getText().toString());
        empresa.setDescripcion(txtDescripcionModificarEmpresa.getText().toString());
        empresa.setId(idEmpresa);

        DataUpdateEmpresa task = new DataUpdateEmpresa(empresa, getApplicationContext());
        task.execute();
    }
}