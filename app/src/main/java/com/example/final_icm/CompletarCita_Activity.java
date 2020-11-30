package com.example.final_icm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CompletarCita_Activity extends AppCompatActivity {

    EditText precio;
    String precioReal;
    Button guardar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completar_cita);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        precioReal = getIntent().getStringExtra("Precio");
        id = getIntent().getIntExtra("ID", 0);
        precio = (EditText) findViewById(R.id.editTextNumber);
        precio.setText(String.valueOf(precioReal));
        guardar = (Button) findViewById(R.id.completar_guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion conn = new conexion(getApplicationContext(),"icm_final", null,1);
                SQLiteDatabase db = conn.getWritableDatabase();

                String update = "UPDATE citas SET finalizado=1, precio="+Float.parseFloat(precio.getText().toString())+" WHERE id="+String.valueOf(id);
                db.execSQL(update);

                db.close();
                startActivity(new Intent(CompletarCita_Activity.this, MainActivity.class));
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}