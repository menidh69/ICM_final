package com.example.final_icm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VerCita_Activity extends AppCompatActivity implements View.OnClickListener {

    Button completar,editar,eliminar;
    int cita_id;
    TextView nombre, fecha, hora, precio, servicio;
    String metodo;
    conexion con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ver_cita_activity);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nombre = (TextView) findViewById(R.id.nombre);
        fecha = (TextView) findViewById(R.id.fecha);
        precio = (TextView) findViewById(R.id.precio);
        hora = (TextView) findViewById(R.id.hora);
        servicio = (TextView) findViewById(R.id.servicio);
        cita_id = getIntent().getIntExtra("Cita", 0);

        con = new conexion(this, "icm_final", null, 1);
        SQLiteDatabase db = con.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,nombre,servicio,precio,fecha,hora FROM citas WHERE id="+cita_id,null);

        if(cursor != null) {
            if (cursor.moveToFirst()) {
                nombre.setText(cursor.getString(1));
                servicio.setText(cursor.getString(2));
                precio.setText(String.valueOf(cursor.getFloat(3)));
                fecha.setText(cursor.getString(4));
                hora.setText(cursor.getString(5));

            }
        }


        completar = (Button) findViewById(R.id.completar_btn);
        completar.setOnClickListener(this);
        editar = (Button) findViewById(R.id.editar_cita);
        editar.setOnClickListener(this);
        eliminar = (Button) findViewById(R.id.btn_eliminar);
        eliminar.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.completar_btn:
                Intent intent = new Intent(VerCita_Activity.this, CompletarCita_Activity.class);
                intent.putExtra("Precio", precio.getText());
                intent.putExtra("ID", cita_id);
                startActivity(intent);
                break;
            case R.id.editar_cita:
                Intent intent2 = new Intent(VerCita_Activity.this, EditarCita.class);
                intent2.putExtra("ID", cita_id);
                startActivity(intent2);
                break;
            case R.id.btn_eliminar:
                Intent intent3 = new Intent(VerCita_Activity.this, EliminarCita.class);
                intent3.putExtra("ID", cita_id);
                startActivity(intent3);
                break;
        }
    }
}