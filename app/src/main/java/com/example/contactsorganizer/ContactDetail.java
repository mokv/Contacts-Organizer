package com.example.contactsorganizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Database.DbHelper;
import Models.Contact;
import Utils.ToastService;

public class ContactDetail extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewPhone;
    private TextView textViewDescription;
    private TextView textViewCategory;
    private Button buttonBack;
    private Button buttonEdit;
    private Button buttonDelete;
    private ToastService toastService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Intent intent = getIntent();
        final Contact contact = (Contact) intent.getSerializableExtra("contact");

        this.textViewName = findViewById(R.id.textViewName);
        this.textViewName.setText(contact.getName());

        this.textViewPhone = findViewById(R.id.textViewPhone);
        this.textViewPhone.setText(contact.getPhone());

        this.textViewDescription = findViewById(R.id.textViewDescription);
        this.textViewDescription.setText(contact.getDescription());

        this.textViewCategory = findViewById(R.id.textViewCategory);
        this.textViewCategory.setText(contact.getCategory());

        toastService = new ToastService(this);

        this.buttonBack = findViewById(R.id.buttonBack);
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Button Edit
        this.buttonEdit = findViewById(R.id.buttonEdit);
        this.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactDetail.this, EditContact.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });

        // Button Delete
        this.buttonDelete = findViewById(R.id.buttonDelete);
        this.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setCancelable(false);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you want to delete this contact?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbHelper dbHelper = new DbHelper(getBaseContext());

                        if (dbHelper.Delete(contact.getId())) {
                            Intent intent = new Intent(ContactDetail.this, MainActivity.class);
                            startActivity(intent);

                            toastService.RaiseMessage("Contact was deleted successfully.");
                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
                            builder1.setCancelable(false);
                            builder1.setMessage("Something went wrong.");
                            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder1.create().show();
                        }
                    }
                });

                builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.create().show();
            }
        });
    }
}
