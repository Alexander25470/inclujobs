package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataInsertEmpresa;
import com.example.inclujobs.conexion.DataObtenerCiudades;
import com.example.inclujobs.conexion.DataObtenerProvincias;
import com.example.inclujobs.conexion.DataObtenerSectores;
import com.example.inclujobs.databinding.ActivityRegistroEmpresaBinding;
import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Provincia;
import com.example.inclujobs.entidades.Sector;
import com.example.inclujobs.entidades.Usuario;
import com.example.inclujobs.helpers.ICallBack;

import java.util.ArrayList;

public class registro_empresa extends AppCompatActivity {
    private Button btnRegistrarseEmpresa;
    private TextView txtNombreEmpresa, txtApellidoEmpresa, txtEmailEmpresa, txtContraEmpresa, txtRepetirContraEmpresa, txtTelefonoEmpresa,
    txtNombreComercial, txtRazonSocial, txtCuit, txtDireccion, txtDescripcion;
    private Spinner spProvincia, spCiudad, spSector;
    private ActivityRegistroEmpresaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registro_empresa);

        binding = ActivityRegistroEmpresaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtNombreEmpresa = findViewById(R.id.txtNombreEmpresa);
        txtApellidoEmpresa = findViewById(R.id.txtApellidoEmpresa);
        txtEmailEmpresa = findViewById(R.id.txtEmailEmpresa);
        txtContraEmpresa = findViewById(R.id.txtContraEmpresa);
        txtRepetirContraEmpresa = findViewById(R.id.txtRepetirContraEmpresa);
        txtTelefonoEmpresa = findViewById(R.id.txtTelefonoEmpresa);
        txtNombreComercial = findViewById(R.id.txtNombreComercial);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtCuit = findViewById(R.id.txtCuit);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        spProvincia = findViewById(R.id.spProvincia);
        spCiudad = findViewById(R.id.spCiudad);
        spSector = findViewById(R.id.spSector);

        Context ctx = this;
        DataObtenerProvincias task = new DataObtenerProvincias(new ICallBack() {
            @Override
            public void function(Object obj) {
                ArrayList<Provincia> listaProvincias = (ArrayList<Provincia>)obj;
                ArrayAdapter<Provincia> adapterProvincias = new ArrayAdapter<Provincia>(ctx, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaProvincias);
                spProvincia.setAdapter(adapterProvincias);
                cargarCiudades();
            }
        });

        task.execute();

        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cargarCiudades();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DataObtenerSectores task2 = new DataObtenerSectores(new ICallBack() {
            @Override
            public void function(Object obj) {
                ArrayList<Sector> listaSectores = (ArrayList<Sector>)obj;
                ArrayAdapter<Sector> adapterSectores = new ArrayAdapter<Sector>(ctx, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaSectores);
                spSector.setAdapter(adapterSectores);
            }
        });

        task2.execute();

        btnRegistrarseEmpresa = findViewById(R.id.btnRegistrarseEmpresa);
        btnRegistrarseEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarEmpresa();
            }
        });
    }

    private void cargarCiudades(){
        Provincia prov = (Provincia)spProvincia.getSelectedItem();
        Context ctx = this;
        DataObtenerCiudades task = new DataObtenerCiudades(prov.getId(), new ICallBack() {
            @Override
            public void function(Object obj) {
                ArrayList<Ciudad> listaCiudades = (ArrayList<Ciudad>)obj;
                ArrayAdapter<Ciudad> adapter = new ArrayAdapter<Ciudad>(ctx, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaCiudades);
                spCiudad.setAdapter(adapter);
            }
        });
        task.execute();
    }

    public void agregarEmpresa(){
        Empresa emp = new Empresa();
        Usuario usr = new Usuario();
        Ciudad ciu = new Ciudad();

        if( txtNombreEmpresa.getText().toString() == null || txtNombreEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Nombre", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtApellidoEmpresa.getText().toString() == null || txtApellidoEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Apellido", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtEmailEmpresa.getText().toString() == null || txtEmailEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Email", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtContraEmpresa.getText().toString() == null || txtContraEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar una Contraseña", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else{
            if( txtRepetirContraEmpresa.getText().toString() == null || txtRepetirContraEmpresa.getText().toString().isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(),"Debe repetir la Contraseña", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }else{
                if( !txtContraEmpresa.getText().toString().equals(txtRepetirContraEmpresa.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
            }
        }

        if( txtTelefonoEmpresa.getText().toString() == null || txtTelefonoEmpresa.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtNombreComercial.getText().toString() == null || txtNombreComercial.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtRazonSocial.getText().toString() == null || txtRazonSocial.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtCuit.getText().toString() == null || txtCuit.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtDireccion.getText().toString() == null || txtDireccion.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if( txtDescripcion.getText().toString() == null || txtDescripcion.getText().toString().isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar un Teléfono", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        usr.setNombre(txtNombreEmpresa.getText().toString());
        usr.setApellido(txtApellidoEmpresa.getText().toString());
        usr.setEmail(txtEmailEmpresa.getText().toString());
        usr.setContra(txtContraEmpresa.getText().toString());
        usr.setTelefono(txtTelefonoEmpresa.getText().toString());
        ciu.setId(((Ciudad) spCiudad.getSelectedItem()).getId());
        emp.setNombreComercial(txtNombreComercial.getText().toString());
        emp.setRazonSocial(txtRazonSocial.getText().toString());
        emp.setCuit(txtCuit.getText().toString());
        emp.setDireccion(txtDireccion.getText().toString());
        emp.setDescripcion(txtDescripcion.getText().toString());
        emp.setUsuarioDuenio(usr);
        emp.setCiudad(ciu);

        DataInsertEmpresa task = new DataInsertEmpresa(emp, getApplicationContext());
        task.execute();

    }
}