package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclujobs.MainActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertUsuario;
import com.example.inclujobs.conexion.DataObtenerTiposDiscapacidades;
import com.example.inclujobs.databinding.ActivityRegistroUsuarioBinding;
import com.example.inclujobs.entidades.TipoDiscapacidad;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.example.inclujobs.helpers.REGEX;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity {
    private Button btnRegistrarseUsuario, btnCancelarRegistrarseUsuario;
    private TextView txtNombre, txtApellido, txtEmail, txtContra, txtRepetirContra, txtTelefono;
    private Spinner spTipoDiscapacidad;
    private ActivityRegistroUsuarioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registro_usuario);
       //View v = inflater.inflate(R.layout.activity_registro_usuario, container, false);

        binding = ActivityRegistroUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        txtContra = findViewById(R.id.txtContra);
        txtRepetirContra = findViewById(R.id.txtRepetirContra);
        txtTelefono = findViewById(R.id.txtTelefono);
        spTipoDiscapacidad = findViewById(R.id.spTipoDiscapacidad);

        Context ctx = this;
        DataObtenerTiposDiscapacidades task = new DataObtenerTiposDiscapacidades(new ICallBack() {
            @Override
            public void function(Object obj) {
                ArrayList<TipoDiscapacidad> listaTiposDiscapacidades = (ArrayList<TipoDiscapacidad>)obj;
                ArrayAdapter<TipoDiscapacidad> adapterCategorias = new ArrayAdapter<TipoDiscapacidad>(ctx, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaTiposDiscapacidades);
                spTipoDiscapacidad.setAdapter(adapterCategorias);
            }
        });

        task.execute();

        btnRegistrarseUsuario = findViewById(R.id.btnRegistrarseUsuario);
        btnRegistrarseUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarUsuario();
            };
        });

        btnCancelarRegistrarseUsuario = findViewById(R.id.btnCancelarRegistrarseUsuario);
        btnCancelarRegistrarseUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void agregarUsuario() {
        Usuario usr = new Usuario();
        TipoDiscapacidad tipoDiscapacidad = new TipoDiscapacidad();
        String email = txtEmail.getText().toString();

        if(txtNombre.length()<3){
            Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar un Nombre valido", Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }
        if(txtApellido.length()<3){
            Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar un Apellido valido", Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        if (txtNombre.getText().toString() == null || txtNombre.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar un Nombre", Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        if (txtApellido.getText().toString() == null || txtApellido.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar un Apellido", Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }

        if( email == null || email.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(!REGEX.EMAIL.matcher(email).matches()){
            Toast toast = Toast.makeText(this,"Debe ingresar un Email valido", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (txtContra.getText().toString() == null || txtContra.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar una Contrase??a", Toast.LENGTH_SHORT);
            toast.show();
            return ;
        } else {
            if (txtRepetirContra.getText().toString() == null || txtRepetirContra.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Debe repetir la Contrase??a", Toast.LENGTH_SHORT);
                toast.show();
                return ;
            } else {
                if (!txtContra.getText().toString().equals(txtRepetirContra.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Las contrase??as no coinciden", Toast.LENGTH_SHORT);
                    toast.show();
                    return ;
                }
            }
        }

        if (txtTelefono.getText().toString() == null || txtTelefono.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar un Tel??fono", Toast.LENGTH_SHORT);
            toast.show();
            return ;
        }
        tipoDiscapacidad.setId(((TipoDiscapacidad) spTipoDiscapacidad.getSelectedItem()).getId());
        usr.setNombre(txtNombre.getText().toString());
        usr.setApellido(txtApellido.getText().toString());
        usr.setEmail(email);
        usr.setContra(txtContra.getText().toString());
        usr.setTelefono(txtTelefono.getText().toString());
        usr.setTipoDiscapacidad(tipoDiscapacidad);

        Context ctx = this;
        DataInsertUsuario task = new DataInsertUsuario(usr, new ICallBack() {
            @Override
            public void function(Object obj) {
                if((int)obj == 1){
                    Toast toast = Toast.makeText(ctx,"Usuario registrado", Toast.LENGTH_SHORT);
                    toast.show();

                    finish();
                } else {
                    Toast toast = Toast.makeText(ctx,"Error al registrar Usuario", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        task.execute();
    }


}