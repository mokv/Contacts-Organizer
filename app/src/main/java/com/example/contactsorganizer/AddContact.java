package com.example.contactsorganizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import Database.DbHelper;
import Models.Contact;
import Utils.ToastService;

public class AddContact extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonSave;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextDescription;
    private String category;
    private Spinner spinner;
    private ToastService toastService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Fields
        this.editTextName = findViewById(R.id.editTextName);
        this.editTextPhone = findViewById(R.id.editTextPhone);
        this.editTextDescription = findViewById(R.id.editTextDescription);

        // Categories
        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("Work");
        categoryList.add("Family");
        categoryList.add("Friends");
        categoryList.add("Colleagues");

        // Spinner
        spinner = findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spinnerText, categoryList);
        spinner.setAdapter(adapter);

        // Toast
        toastService = new ToastService(this);

        // Button Back
        this.buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddContact.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Button Save
        this.buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dependencies
                DbHelper dbHelper = new DbHelper(getBaseContext());
                Contact contact = new Contact();

                // Set properties
                contact.setName(editTextName.getText().toString());
                contact.setPhone(editTextPhone.getText().toString());
                contact.setDescription(editTextDescription.getText().toString());

                // Set the value from the spinner
                category = spinner.getSelectedItem().toString();
                contact.setCategory(category);

                if (dbHelper.Create(contact)) {
                    Intent intent = new Intent(AddContact.this, MainActivity.class);
                    startActivity(intent);

                    toastService.RaiseMessage("Contact was added successfully.");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setMessage("Something went wrong..");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.create().show();
                }
            }
        });
    }
}