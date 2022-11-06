package com.example.inclujobs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.inclujobs.DTOs.CVListadoDTO;
import com.example.inclujobs.R;

import java.util.ArrayList;

public class CVAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CVListadoDTO> cvs;

    public CVAdapter(Context context, ArrayList<CVListadoDTO> cvs) {
        this.context = context;
        this.cvs = cvs;
    }

    @Override
    public int getCount() {
        return cvs.size();
    }

    @Override
    public Object getItem(int i) {
        return cvs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CVListadoDTO cv = (CVListadoDTO)getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.list_item_cv, null);
        TextView tvTitulo = view.findViewById(R.id.tvTituloItemCV),
                tvTipoDiscapacidad = view.findViewById(R.id.tvTipoDiscapacidadItemCV);

        tvTitulo.setText(cv.getNombreArchivo());
        tvTipoDiscapacidad.setText("Tipo de discapacidad: " + cv.getNombreTipoDiscapacidad());

        return view;
    }
}
