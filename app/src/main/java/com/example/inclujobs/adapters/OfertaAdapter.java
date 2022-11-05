package com.example.inclujobs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.inclujobs.R;
import com.example.inclujobs.entidades.Empresa;
import com.example.inclujobs.entidades.Oferta;

import java.util.ArrayList;

public class OfertaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Oferta> ofertas;

    public OfertaAdapter(Context context, ArrayList<Oferta> ofertas) {
        this.context = context;
        this.ofertas = ofertas;
    }

    @Override
    public int getCount() {
        return ofertas.size();
    }

    @Override
    public Object getItem(int i) {
        return ofertas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Oferta oferta = (Oferta)getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.list_item_oferta, null);
        TextView tvTitulo = view.findViewById(R.id.tvTituloItemOferta),
                tvEmpresa = view.findViewById(R.id.tvEmpesaItemOferta),
                tvDescripcion = view.findViewById(R.id.tvDescripcionItemOferta),
                tvSalario = view.findViewById(R.id.tvSalarioItemOferta);

        tvTitulo.setText(oferta.getTitulo());
        tvDescripcion.setText(oferta.getDescripcion());
        tvSalario.setText("Salario: $"+oferta.getSalario());
        tvEmpresa.setText(oferta.getEmpresa().getNombreComercial());

        return view;
    }
}
