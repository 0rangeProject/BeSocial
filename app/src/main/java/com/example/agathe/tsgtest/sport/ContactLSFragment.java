package com.example.agathe.tsgtest.sport;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.ContactLS;

import java.util.ArrayList;

public class ContactLSFragment extends Fragment {

private ListView contactsList;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts_little_services, container, false);
        contactsList = (ListView) rootView.findViewById(R.id.contacts_ls_list);
        setupList();
        return rootView;
        }

private void setupList() {
        contactsList.setAdapter(createAdapter());
        contactsList.setOnItemClickListener(new ListItemClickListener());
        }

private ContactLSAdapter createAdapter() {
        ArrayList<String> itemsName = new ArrayList<>();
        ArrayList<String> itemsLocation = new ArrayList<>();

        ArrayList<ContactLS> contact_challenge = new ArrayList<ContactLS>() {{
            add(new ContactLS("Sarah Liousse", "Close Friend (around 10km)"));
            add(new ContactLS("Arthur Dubois", "Known (around 50km)"));
            add(new ContactLS("Martin Delarre", "Known (around 300km)"));
            add(new ContactLS("Lili Rose", "New Friend (around 200km)"));;
        }};

        for (int i = 0; i < contact_challenge.size(); i++) {
        itemsName.add(contact_challenge.get(i).ls_name);
        itemsLocation.add(contact_challenge.get(i).location);
        }

        return new ContactLSAdapter(getActivity(), itemsName, itemsLocation, new ListItemButtonClickListener(), new ListItemButtonClickListener());
        }

private final class ListItemButtonClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        for (int i = contactsList.getFirstVisiblePosition(); i <= contactsList.getLastVisiblePosition(); i++) {
            if (v == contactsList.getChildAt(i - contactsList.getFirstVisiblePosition()).findViewById(R.id.list_item_cc_button_sms)) {
                Toast.makeText(getActivity(), "Clicked on Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.putExtra("address", "0649153247");
                smsIntent.putExtra("sms_body","Hello, Can you loan me your tool please ?");
                smsIntent.setData(Uri.parse("smsto:" + "0649153247"));
                startActivity(smsIntent);
            }
        }
    }
}

private final class ListItemClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Clicked on List Item " + position, Toast.LENGTH_SHORT).show();
    }
}
}




