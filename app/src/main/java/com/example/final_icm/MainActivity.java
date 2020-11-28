package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    ImageButton nuevaCita, addContact;
    Button nServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        conexion con = new conexion(this, "icm_final", null, 1);

        nuevaCita = (ImageButton) findViewById(R.id.NuevaCita);
        nServicio = (Button) findViewById(R.id.btn_nServicio);
        addContact = (ImageButton) findViewById(R.id.addContact);
        addContact.setOnClickListener(this);
        nServicio.setOnClickListener(this);
        nuevaCita.setOnClickListener(this);

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

   

}