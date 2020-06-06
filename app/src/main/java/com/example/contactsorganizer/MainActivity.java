package com.example.contactsorganizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import Adapters.ContactsAdapter;
import Database.DbHelper;
import Models.Contact;
import Utils.ToastService;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd;
    private ListView listViewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DbHelper dbHelper = new DbHelper(this);

        this.buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });

        // All Contacts List View
        this.listViewContacts = findViewById(R.id.listViewContacts);
        this.listViewContacts.setAdapter(new ContactsAdapter(this, dbHelper.GetAll()));

        this.listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = dbHelper.GetAll().get(position);
                Intent intent  = new Intent(MainActivity.this, ContactDetail.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });
    }
}