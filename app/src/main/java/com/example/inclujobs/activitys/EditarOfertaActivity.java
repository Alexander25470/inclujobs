package com.example.inclujobs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import com.example.inclujobs.R;
import com.example.inclujobs.entidades.Oferta;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EditarOfertaActivity extends AppCompatActivity {
private Oferta oferta;
private TextView tvTituloModificar, tvDescripcionModificar, tvSalarioModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_oferta);

        Intent intent = getIntent();
        Gson gson = new Gson();
        oferta = gson.fromJson(intent.getStringExtra("oferta"), Oferta.class);

        tvTituloModificar = findViewById(R.id.tvTituloModificar);
        tvDescripcionModificar = findViewById(R.id.tvDescripcionModificar);
        tvSalarioModificar = findViewById(R.id.tvSalarioModificar);

        tvTituloModificar.setText(oferta.getTitulo());
        tvDescripcionModificar.setText(oferta.getDescripcion());
        tvSalarioModificar.setText(oferta.getSalario().toString());

    }

    public void

}