package com.example.final_icm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton addContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addContact = (ImageButton) findViewById(R.id.addContact);
        addContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addContact:
                addContactfunction();
        }
    }

    public void addContactfunction(){
        Intent intent = new Intent(Intent.ACTION_INSERT,
                ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

}