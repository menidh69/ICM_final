package com.example.final_icm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.final_icm.tablas.Servicios;

import java.util.ArrayList;

public class NuevaCita extends AppCompatActivity {

    Spinner comboServicios;
    ArrayList<String> listaServicios;
    ArrayList<Servicios> serviciosLista;

    conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cita);

        con = new conexion(getApplicationContext(),"icm_final", null,1);

        comboServicios = (Spinner) findViewById(R.id.comboServicios);

        consultarListaServicios();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listaServicios);

        comboServicios.setAdapter(adaptador);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void consultarListaServicios() {
        SQLiteDatabase db = con.getWritableDatabase();

        Servicios ser = null;
        serviciosLista = new ArrayList<Servicios>();

        Cursor cursor = db.rawQuery("SELECT * FROM servicios",null);

        while(cursor.moveToNext()){
            ser = new Servicios();
            ser.setId(cursor.getInt(0));
            ser.setNombre(cursor.getString(1));
            ser.setDescripcion(cursor.getString(2));
            ser.setPrecio(cursor.getFloat(3));

            serviciosLista.add(ser);

        }
        obtenerServicios();


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

    private void obtenerServicios() {

        listaServicios = new ArrayList<String>();
        listaServicios.add("Seleccione");

        for (int i = 0; i < serviciosLista.size(); i++) {
            listaServicios.add(serviciosLista.get(i).getNombre());
        }
    }

}