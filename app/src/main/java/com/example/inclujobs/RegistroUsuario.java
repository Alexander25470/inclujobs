package com.example.inclujobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclujobs.conexion.DataInsertUsuario;
import com.example.inclujobs.databinding.ActivityRegistroUsuarioBinding;
import com.example.inclujobs.entidades.Usuarios;

public class RegistroUsuario extends AppCompatActivity {
    private Button btnRegistrarseUsuario;
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

        btnRegistrarseUsuario = findViewById(R.id.btnRegistrarseUsuario);
        btnRegistrarseUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarUsuario();
            }
        });
    }

    public void agregarUsuario(){
        Usuarios usr = new Usuarios();

        if( txtNombre.getText().toString() == null || txtNombre.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Nombre", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtApellido.getText().toString() == null || txtApellido.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Apellido", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtEmail.getText().toString() == null || txtEmail.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtContra.getText().toString() == null || txtContra.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar una Contraseña", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtRepetirContra.getText().toString() == null || txtRepetirContra.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe repetir la Contraseña", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtTelefono.getText().toString() == null || txtTelefono.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        usr.setNombre(txtNombre.getText().toString());
        usr.setApellido(txtApellido.getText().toString());
        usr.setEmail(txtEmail.getText().toString());
        usr.setContra(txtContra.getText().toString());
        usr.setTelefono(txtTelefono.getText().toString());

        DataInsertUsuario task = new DataInsertUsuario(usr, getApplicationContext());
        task.execute();

    }
}