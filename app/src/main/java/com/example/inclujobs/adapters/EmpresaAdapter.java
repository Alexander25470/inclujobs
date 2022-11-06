package com.example.inclujobs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.inclujobs.R;
import com.example.inclujobs.entidades.Ciudad;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;

import java.util.ArrayList;

public class EmpresaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Empresa> empresas;

    public EmpresaAdapter(Context context, ArrayList<Empresa> empresas) {
        this.context = context;
        this.empresas = empresas;
    }

    @Override
    public int getCount() {
        return empresas.size();
    }

    @Override
    public Object getItem(int i) {
        return empresas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Empresa empresa = (Empresa)getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.list_item_empresa, null);
        TextView tvTitulo = view.findViewById(R.id.tvTituloItemEmpresa),
                tvUbicacion = view.findViewById(R.id.tvUbicacionItemEmpresa),
                tvDescripcion = view.findViewById(R.id.tvDescripcionItemEmpresa);

        tvTitulo.setText(empresa.getNombreComercial());
        tvDescripcion.setText(empresa.getDescripcion());
        Ciudad ciudad = empresa.getCiudad();
        tvUbicacion.setText(ciudad.getProvincia().getNombre() + ", " + ciudad.getNombre());

        return view;
    }
}
