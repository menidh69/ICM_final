package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button nuevaCita, nServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion con = new conexion(this, "icm_final", null, 1);

        nuevaCita = (Button) findViewById(R.id.NuevaCita);
        nServicio = (Button) findViewById(R.id.btn_nServicio);

        nuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NuevaCita.class));
            }
        });

        nServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NuevoServicio.class));
            }
        });
    }
}