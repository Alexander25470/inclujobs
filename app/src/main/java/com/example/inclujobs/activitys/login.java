package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertEmpresa;
import com.example.inclujobs.conexion.DataLogin;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;
import com.google.gson.Gson;

public class login extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etContraLogin);
    }

    public void openRegistroEmpresa(View v){
        Intent intent = new Intent(this, registro_empresa.class);
        startActivity(intent);
    }

    public void openRegistroUsuario(View v){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    public void login(View v){
        String email = etEmail.getText().toString(),
        pass = etPass.getText().toString();

        if(email.isEmpty())
        {
            Toast toast = Toast.makeText(this,"Debe ingresar un Email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(pass.isEmpty())
        {
            Toast toast = Toast.makeText(this,"Debe ingresar contraseña", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Context ctx = this;
        DataLogin task = new DataLogin(email, pass, new ICallBack() {
            @Override
            public void function(Object obj) {
                if(obj == null){
                    Toast toast = Toast.makeText(ctx,"Credenciales invalidas", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Usuario user = (Usuario) obj;

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                Gson gson = new Gson();
                String userJson = gson.toJson(user);
                editor.putString(getString(R.string.logged_user_key), userJson);
                editor.apply();

                Toast toast = Toast.makeText(ctx,"Sesion iniciada", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        });
        task.execute();
    }
}