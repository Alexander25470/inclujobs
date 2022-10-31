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
        btnRegistroUsuario = (Button) findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnBuscarLugar = (Button) findViewById(R.id.btnBuscarLugar);


        user = UserHelper.getUser(this);

        if(user == null){
            btnIniciarSesion.setVisibility(View.GONE);
            btnRegistroEmpresa.setVisibility(View.GONE);
            btnRegistroUsuario.setVisibility(View.GONE);
            btnBuscarLugar.setVisibility(View.GONE);
        }
    }

    public void openRegistroEmpresa(View v){
        Intent intent = new Intent(this, registro_empresa.class);
        startActivity(intent);
    }

    public void openRegistroUsuario(View v){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void openIniciarSesion(View v){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void openBuscarLugar(View v){
        Intent intent = new Intent(this, ListadoEmpresasActivity.class);
        startActivity(intent);
    }

}