package com.example.agathe.tsgtest.littleservices;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.ContactLS;

import java.util.ArrayList;

public class AskingLSFragment extends Fragment {

private ListView contactsList;
    public EditText service_text;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_asking_little_services, container, false);
        contactsList = (ListView) rootView.findViewById(R.id.contacts_ls_list);
     service_text = (EditText) rootView.findViewById(R.id.little_service_text);
    //service_text.setText("");
    setupList();
        return rootView;
}

private void setupList() {
        contactsList.setAdapter(createAdapter());
        contactsList.setOnItemClickListener(new ListItemClickListener());
        }

private AskingLSAdapter createAdapter() {
        ArrayList<String> itemsName = new ArrayList<>();
        ArrayList<String> itemsRelationStrength = new ArrayList<>();

        ArrayList<ContactLS> asking_contacts = new ArrayList<ContactLS>() {{
            add(new ContactLS("Sarah Liousse", "Very frequent contact"));
            add(new ContactLS("Arthur Dubois", "Frequent contact"));
            add(new ContactLS("Martin Delarre", "Frequent contact"));
            add(new ContactLS("Lili Rose", "Occasional contact"));
        }};

        for (int i = 0; i < asking_contacts.size(); i++) {
        itemsName.add(asking_contacts.get(i).ls_name);
            itemsRelationStrength.add(asking_contacts.get(i).relation_strength);
        }

        return new AskingLSAdapter(getActivity(), itemsName, itemsRelationStrength, new ListItemButtonClickListener(), new ListItemButtonClickListener());
        }

private final class ListItemButtonClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        for (int i = contactsList.getFirstVisiblePosition(); i <= contactsList.getLastVisiblePosition(); i++) {
            //sms intent
            if (v == contactsList.getChildAt(i - contactsList.getFirstVisiblePosition()).findViewById(R.id.list_item_asking_button_sms)) {
                if (service_text.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please complete the message with a service to ask ", Toast.LENGTH_LONG).show();
                }
                if (!service_text.getText().toString().isEmpty()) {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.putExtra("address", "0649153247");
                    smsIntent.putExtra("sms_body", "Hello, Can you loan me your " + service_text.getText().toString() + " please ?");
                    smsIntent.setData(Uri.parse("smsto:" + "0649153247"));
                    startActivity(smsIntent);
                }
            }
//calling intent
            if (v == contactsList.getChildAt(i - contactsList.getFirstVisiblePosition()).findViewById(R.id.list_item_asking_button_call)) {
                String phone = "+33649153247";
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(callIntent);
            }
        }
    }
}

private final class ListItemClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getActivity(), " " + position, Toast.LENGTH_SHORT).show();
    }
}
}




