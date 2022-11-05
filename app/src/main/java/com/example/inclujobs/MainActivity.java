package com.example.inclujobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.inclujobs.activitys.ListadoEmpresasActivity;
import com.example.inclujobs.activitys.ListadoOfertasActivity;
import com.example.inclujobs.activitys.RegistroUsuario;
import com.example.inclujobs.activitys.LoginActivity;
import com.example.inclujobs.activitys.registro_empresa;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.UserHelper;

public class MainActivity extends AppCompatActivity {
    private Button btnRegistroEmpresa;
    private Button btnRegistroUsuario;
    private Button btnIniciarSesion;
    private Button btnBuscarLugar;
    private Button btnBuscarEmpleos;
    private Button btnBuscarEmpresas;
    private Usuario user;
    private String busquedaSeleccionada;
    private final int LOGINRESULT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ocultar action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        btnRegistroEmpresa = (Button) findViewById(R.id.btnRegistroEmpresa);
        btnRegistroUsuario = (Button) findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnBuscarLugar = (Button) findViewById(R.id.btnBuscarLugar);
        btnBuscarEmpleos = (Button) findViewById(R.id.btnEmpleos);
        btnBuscarEmpresas = (Button) findViewById(R.id.btnEmpresas);

        seleccionarBusqueda("empleos");

        user = UserHelper.getUser(this);

        if(user != null){
            btnRegistroEmpresa.setVisibility(View.GONE);
            btnRegistroUsuario.setVisibility(View.GONE);
            btnIniciarSesion.setText("Cerrar sesion");
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
        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGINRESULT);
        } else {
            UserHelper.removeUser(this);
            user = null;
            btnRegistroEmpresa.setVisibility(View.VISIBLE);
            btnRegistroUsuario.setVisibility(View.VISIBLE);
            btnIniciarSesion.setText("Iniciar sesion");
        }

    }

    public void openBuscar(View v){
        Class<?> viewToOpen = busquedaSeleccionada == "empleos"? ListadoOfertasActivity.class : ListadoEmpresasActivity.class;
        Intent intent = new Intent(this, viewToOpen);
        startActivity(intent);
    }

    public void seleccionarEmpleos(View v){
        seleccionarBusqueda("empleos");
    }

    public void seleccionarEmpresas(View v){
        seleccionarBusqueda("empresas");
    }

    public void seleccionarBusqueda(String tipoBusqueda){
        busquedaSeleccionada = tipoBusqueda;
        if(tipoBusqueda.contentEquals("empleos")){
            btnBuscarEmpleos.getBackground().setAlpha(127);
            btnBuscarEmpresas.getBackground().setAlpha(255);
        } else {
            btnBuscarEmpleos.getBackground().setAlpha(255);
            btnBuscarEmpresas.getBackground().setAlpha(127);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == LoginActivity.RESULT_OK)
        {
            user = UserHelper.getUser(this);

            if(user != null){
                btnRegistroEmpresa.setVisibility(View.GONE);
                btnRegistroUsuario.setVisibility(View.GONE);
                btnIniciarSesion.setText("Cerrar sesion");
            }
        }
    }
}