package com.example.final_icm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.final_icm.tablas.Citas;

import java.util.ArrayList;
import java.util.List;

public class CitasCompletadas_Activity extends AppCompatActivity {

    TableLayout tablacompletada;
    List<Citas> citasArray;
    conexion con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_completadas_);
        con = new conexion(this, "icm_final", null, 1);
        tablacompletada = (TableLayout) findViewById(R.id.tablacompletada);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        consultarCitas();

    }
    private void consultarCitas() {
        SQLiteDatabase db = con.getWritableDatabase();

        Citas citas = null;
        citasArray = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM citas WHERE finalizado=1", null);

        while (cursor.moveToNext()) {
            citas = new Citas();
            citas.setId(cursor.getInt(0));
            citas.setNombre(cursor.getString(1));
            citas.setServicio(cursor.getString(2));
            citas.setPrecio(cursor.getFloat(3));
            citas.setFecha(cursor.getString(4));
            citas.setHora(cursor.getString(5));
            citasArray.add(citas);
        }

        TextView fecha, precio, nombre;

        for (int i = 0; i < citasArray.size(); i++) {
            Citas c = citasArray.get(i);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            nombre = new TextView(this);
            nombre.setText(c.getNombre().toString());
            nombre.setTextSize(18);
            fecha = new TextView(this);
            fecha.setText(c.getFecha());
            fecha.setTextSize(18);
            precio = new TextView(this);
            precio.setText(String.valueOf(c.getPrecio()));
            precio.setTextSize(18);
            row.addView(fecha);
            row.addView(nombre);
            row.addView(precio);


            tablacompletada.addView(row);
        }
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