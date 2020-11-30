package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.final_icm.tablas.Citas;
import com.example.final_icm.tablas.Servicios;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditarCita extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText et_nombre, edt_fecha, edt_hora;
    Spinner comboServicios;
    TextView precio;
    int id;
    conexion con;
    ArrayList<String> listaServicios;
    ArrayList<Servicios> serviciosLista;
    Button guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cita);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        edt_fecha = (EditText) findViewById(R.id.edt_fecha);
        edt_hora = (EditText) findViewById(R.id.edt_hora);
        comboServicios = (Spinner) findViewById(R.id.comboServicios);
        precio = (TextView) findViewById(R.id.precio);
        id = getIntent().getIntExtra("ID", 0);
        guardar = (Button) findViewById(R.id.btn_guardar);



        con = new conexion(getApplicationContext(),"icm_final", null,1);
        SQLiteDatabase db = con.getWritableDatabase();

        Citas cita = null;
        float pre;

        Cursor cursor = db.rawQuery("SELECT * FROM citas WHERE id = " + id, null);

        listaServicios = new ArrayList<String>();

        while(cursor.moveToNext()){
            cita = new Citas();
            cita.setNombre(cursor.getString(1));
            cita.setServicio(cursor.getString(2));
            cita.setPrecio(cursor.getFloat(3));
            cita.setFecha(cursor.getString(4));
            cita.setHora(cursor.getString(5));

            et_nombre.setText(cita.getNombre());
            listaServicios.add(cita.getServicio());
            edt_fecha.setText(cita.getFecha());
            edt_hora.setText(cita.getHora());
            precio.setText(Float.toString(cita.getPrecio()));
        }

        consultarListaServicios();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listaServicios);

        comboServicios.setAdapter(adaptador);

        et_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        edt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        edt_hora.setOnClickListener(new View.OnClickListener() {
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

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conexion conn = new conexion(getApplicationContext(),"icm_final", null,1);
                SQLiteDatabase db = conn.getWritableDatabase();

                String insert = "UPDATE citas SET nombre = \"" + et_nombre.getText().toString() + "\", servicio = \"" + comboServicios.getSelectedItem().toString() + "\", precio = " + Float.parseFloat(precio.getText().toString()) +
                        ", fecha = \"" + edt_fecha.getText().toString() + "\", hora = \"" + edt_hora.getText().toString() + "\" WHERE id = " + id;
                db.execSQL(insert);

                db.close();

                startActivity(new Intent(EditarCita.this, MainActivity.class));
            }
        });

    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case 1 :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        et_nombre.setText(name);
                    }
                }
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        String currentTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        edt_hora.setText(currentTimeString);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        edt_fecha.setText(currentDateString);
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

    private void obtenerServicios() {

        listaServicios = new ArrayList<String>();


        for (int i = 0; i < serviciosLista.size(); i++) {
            listaServicios.add(serviciosLista.get(i).getNombre());
        }
    }
}