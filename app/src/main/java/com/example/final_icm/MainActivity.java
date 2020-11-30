package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_icm.tablas.Citas;
import com.example.final_icm.tablas.Servicios;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    ImageButton nuevaCita, addContact;
    Button nServicio;
    conexion con;
    List<Citas> citasArray;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        con = new conexion(this, "icm_final", null, 1);

        nuevaCita = (ImageButton) findViewById(R.id.NuevaCita);
        nServicio = (Button) findViewById(R.id.btn_nServicio);
        addContact = (ImageButton) findViewById(R.id.addContact);
        table = (TableLayout) findViewById(R.id.table);
        addContact.setOnClickListener(this);
        nServicio.setOnClickListener(this);
        nuevaCita.setOnClickListener(this);

        consultarCitas();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addContact:
                addContactfunction();
                break;

            case R.id.NuevaCita:
                startActivity(new Intent(MainActivity.this, NuevaCita.class));
                break;

            case R.id.btn_nServicio:
                startActivity(new Intent(MainActivity.this, NuevoServicio.class));
                break;
        }
    }

    public void addContactfunction(){
        Intent intent = new Intent(Intent.ACTION_INSERT,
                ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    private void consultarCitas() {
        SQLiteDatabase db = con.getWritableDatabase();

        Citas citas = null;
        citasArray = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM citas",null);

        while(cursor.moveToNext()){
            citas = new Citas();
            citas.setId(cursor.getInt(0));
            citas.setNombre(cursor.getString(1));
            citas.setServicio(cursor.getString(2));
            citas.setPrecio(cursor.getFloat(3));
            citas.setFecha(cursor.getString(4));
            citas.setHora(cursor.getString(5));
            citasArray.add(citas);
        }

        TextView fecha, hora, nombre;

        for(int i=0; i<citasArray.size(); i++){
            Citas c = citasArray.get(i);
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            nombre = new TextView(this);
            nombre.setText(c.getNombre().toString());
            nombre.setTextSize(18);
            fecha = new TextView(this);
            fecha.setText(c.getFecha());
            fecha.setTextSize(18);
            hora = new TextView(this);
            hora.setText(c.getHora());
            hora.setTextSize(18);
            row.addView(fecha);
            row.addView(nombre);
            row.addView(hora);
            row.setClickable(true);  //allows you to select a specific row
            row.setOnClickListener(new RowListener(c));
            /*row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    v.setBackgroundColor(Color.GRAY);
                    System.out.println("Row clicked: " + v.getId());



                    v.setBackgroundColor(Color.WHITE);
                    //get the data you need
                    TableRow tablerow = (TableRow) v;
                    TextView sample = (TextView) tablerow.getChildAt(1);
                    String result=sample.getText().toString();

                    Toast toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG);
                    toast.show();
                    Intent i = new Intent();
                    //i.putExtra("Cita", );
                    startActivity(new Intent(MainActivity.this, VerCita_Activity.class));
                }
            });*/
            table.addView(row);
        }



    }

    public class RowListener implements View.OnClickListener
    {

        Citas cita;

        public RowListener(Citas cita) {
            this.cita = cita;
        }

        @Override
        public void onClick(View v)
        {
            v.setBackgroundColor(Color.GRAY);
            System.out.println("Row clicked: " + v.getId());
            v.setBackgroundColor(Color.WHITE);
            //get the data you need
            TableRow tablerow = (TableRow) v;
            TextView sample = (TextView) tablerow.getChildAt(1);
            String result=sample.getText().toString();

            Toast toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG);
            toast.show();
            Intent i = new Intent(MainActivity.this, VerCita_Activity.class);
            i.putExtra("Cita", cita.getId());
            startActivity(i);
        }

    };

}