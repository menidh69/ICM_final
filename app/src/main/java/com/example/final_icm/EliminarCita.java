package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EliminarCita extends AppCompatActivity {

    int id;
    Button eliminar;
    conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cita);
        eliminar = (Button) findViewById(R.id.btn_confirmar);
        id = getIntent().getIntExtra("ID", 0);

        con = new conexion(getApplicationContext(),"icm_final", null,1);


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = con.getWritableDatabase();
                String insert = "DELETE FROM citas WHERE id = " + id;
                db.execSQL(insert);
                db.close();

                startActivity(new Intent(EliminarCita.this, MainActivity.class));
            }
        });
    }
}