package com.example.inclujobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {
    private Button btnRegistroEmpresa;
    private Button btnRegistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegistroEmpresa = (Button) findViewById(R.id.btnRegistroEmpresaLogin);
        btnRegistroEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistroEmpresa();
            }
        });

        btnRegistroUsuario = (Button) findViewById(R.id.btnRegistrarseLogin);
        btnRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistroUsuario();
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
}