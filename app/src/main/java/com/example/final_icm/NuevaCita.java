package com.example.final_icm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.final_icm.tablas.Servicios;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NuevaCita extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Spinner comboServicios;
    ArrayList<String> listaServicios;
    ArrayList<Servicios> serviciosLista;
    TextView tv_precio, precio;
    EditText PlannedDate;
    EditText PlannedHour, Nombre;
    Button crearCita;

    conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cita);
        String servicio;

        con = new conexion(getApplicationContext(),"icm_final", null,1);

        tv_precio = (TextView) findViewById(R.id.tv_precio);
        comboServicios = (Spinner) findViewById(R.id.comboServicios);
        PlannedDate = (EditText) findViewById(R.id.edt_fecha);
        PlannedHour = (EditText) findViewById(R.id.edt_hora);
        precio = (TextView) findViewById(R.id.precio);
        crearCita = (Button) findViewById(R.id.btn_guardar);
        Nombre = (EditText) findViewById(R.id.et_nombre);

        consultarListaServicios();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listaServicios);

        comboServicios.setAdapter(adaptador);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        PlannedHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment hourPicker = new TimePickerFragment();
                hourPicker.show(getSupportFragmentManager(), "hour picker");
            }
        });

        comboServicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SQLiteDatabase d = con.getWritableDatabase();

                Servicios ser = null;
                Float pre;

                Cursor cursor = d.rawQuery("SELECT * FROM servicios WHERE nombre = \"" + comboServicios.getSelectedItem().toString() + "\"",null);


                while(cursor.moveToNext()){
                    ser = new Servicios();
                    ser.setPrecio(cursor.getFloat(3));

                    pre = ser.getPrecio();
                    precio.setText(pre.toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        crearCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion conn = new conexion(getApplicationContext(),"icm_final", null,1);
                SQLiteDatabase db = conn.getWritableDatabase();

                String insert = "INSERT INTO citas (nombre,servicio,precio, fecha, hora, finalizado) values (\"" + Nombre.getText().toString() + "\", \"" + comboServicios.getSelectedItem().toString() + "\", "
                        + Float.parseFloat(precio.getText().toString()) + ", \"" + PlannedDate.getText().toString() + "\", \"" + PlannedHour.getText().toString() +  "\", " + 0 + ")";
                db.execSQL(insert);

                db.close();

                startActivity(new Intent(NuevaCita.this, MainActivity.class));
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        PlannedHour.setText(currentTimeString);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        PlannedDate.setText(currentDateString);
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