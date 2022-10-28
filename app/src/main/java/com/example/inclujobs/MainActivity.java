package com.example.inclujobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnRegistroEmpresa;
    private Button btnRegistroUsuario;
    private Button btnIniciarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistroEmpresa = (Button) findViewById(R.id.btnRegistroEmpresa);
        btnRegistroEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistroEmpresa();
            }
        });

        btnRegistroUsuario = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistroUsuario();
            }
        });

        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIniciarSesion();
            }
        });
    }

    private void openRegistroEmpresa(){
        Intent intent = new Intent(this, registro_empresa.class);
        startActivity(intent);
    }

    private void openRegistroUsuario(){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    private void openIniciarSesion(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}