package com.example.inclujobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inclujobs.activitys.ListadoEmpresasActivity;
import com.example.inclujobs.activitys.RegistroUsuario;
import com.example.inclujobs.activitys.login;
import com.example.inclujobs.activitys.registro_empresa;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.UserHelper;

public class MainActivity extends AppCompatActivity {
    private Button btnRegistroEmpresa;
    private Button btnRegistroUsuario;
    private Button btnIniciarSesion;
    private Button btnBuscarLugar;
    private Usuario user;

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

        btnBuscarLugar = (Button) findViewById(R.id.btnBuscarLugar);
        btnBuscarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBuscarLugar();
            }
        });


        user = UserHelper.getUser(this);

        if(user == null){
            btnIniciarSesion.setVisibility(View.GONE);
            btnRegistroEmpresa.setVisibility(View.GONE);
            btnRegistroUsuario.setVisibility(View.GONE);
            btnBuscarLugar.setVisibility(View.GONE);
        }
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

    private void openBuscarLugar(){
        Intent intent = new Intent(this, ListadoEmpresasActivity.class);
        startActivity(intent);
    }

}