package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.conexion.DataListadoEmpresas;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.helpers.ICallBack;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListadoEmpresasActivity extends AppCompatActivity {
    private ListView lvEmpresas;
    private ArrayList<Empresa> listaEmpresas = new ArrayList<Empresa>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_empresas);

        lvEmpresas = findViewById(R.id.lvEmpresas);
    }

    private void cargarEmpresas(){
        DataListadoEmpresas task = new DataListadoEmpresas(new ICallBack() {
            @Override
            public void function(Object obj) {
                listaEmpresas = (ArrayList<Empresa>)obj;
            }
        });
    }
}