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

public class EditContact extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextDescription;
    private Button buttonBack;
    private Button buttonSave;
    private String category;
    private Spinner spinner;
    private ToastService toastService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Intent intent = getIntent();
        final Contact contact = (Contact) intent.getSerializableExtra("contact");

        this.editTextName = findViewById(R.id.editTextName);
        this.editTextName.setText(contact.getName());

        this.editTextPhone = findViewById(R.id.editTextPhone);
        this.editTextPhone.setText(contact.getPhone());

        this.editTextDescription = findViewById(R.id.editTextDescription);
        this.editTextDescription.setText(contact.getDescription());

        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("Work");
        categoryList.add("Family");
        categoryList.add("Friends");
        categoryList.add("Others");

        spinner = findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.spinnerText, categoryList);
        spinner.setAdapter(adapter);

        // Getting the value out of the db and selecting it into the spinner
        String categoryValue = contact.getCategory();
        int spinnerPosition = adapter.getPosition(categoryValue);
        spinner.setSelection(spinnerPosition);

        toastService = new ToastService(this);

        this.buttonBack = findViewById(R.id.buttonBack);
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContact.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Button Save
        this.buttonSave = findViewById(R.id.buttonSave);
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(getBaseContext());

                contact.setName(editTextName.getText().toString());
                contact.setPhone(editTextPhone.getText().toString());
                contact.setDescription(editTextDescription.getText().toString());

                // Set the value chosen from the spinner
                category = spinner.getSelectedItem().toString();
                contact.setCategory(category);

                // Update the selected contact
                if (dbHelper.Update(contact)) {
                    Intent intent = new Intent(EditContact.this, MainActivity.class);
                    startActivity(intent);

                    toastService.RaiseMessage("Contact was edited successfully.");
                } else {
                    // Display an alert dialog that something went wrong
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setCancelable(false);
                    builder.setMessage("Something went wrong..");

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