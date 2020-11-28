package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NuevoServicio extends AppCompatActivity {

    Button guardar;
    EditText nombre, descripcion, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_servicio);

        nombre = (EditText) findViewById(R.id.edt_nombre);
        descripcion = (EditText) findViewById(R.id.edt_descripcion);
        precio = (EditText) findViewById(R.id.edt_precio);
        guardar = (Button) findViewById(R.id.btn_guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n, d;
                float p;
                n = nombre.getText().toString();
                d = descripcion.getText().toString();
                p = Float.parseFloat(precio.getText().toString());
                registrarServicio(n,d,p);
                startActivity(new Intent(NuevoServicio.this, MainActivity.class));
            }
        });

    }


    private void registrarServicio(String nombre, String descripcion, float precio) {
        conexion con = new conexion(this, "icm_final", null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        String insert = "INSERT INTO servicios (nombre,descripcion,precio) values (\"" + nombre + "\", \"" + descripcion + "\", " + precio + ")";
        db.execSQL(insert);

        db.close();
    }
}