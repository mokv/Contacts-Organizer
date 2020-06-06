package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.contactsorganizer.R;

import java.util.List;

import Models.Contact;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> contactsList;

    public ContactsAdapter(Context context,
                           List<Contact> contactsList)
    {
        super(context, R.layout.contact_layout, contactsList);

        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.contact_layout, parent, false);

        TextView textViewName = view.findViewById(R.id.textViewName);
        textViewName.setText(contactsList.get(position).getName());

        TextView textViewPhone = view.findViewById(R.id.textViewPhone);
        textViewPhone.setText(contactsList.get(position).getPhone());

        return view;
    }
}