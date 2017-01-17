package com.example.agathe.tsgtest.sport;

import android.app.Fragment;
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
import com.example.agathe.tsgtest.dto.ContactChallenge;

import java.util.ArrayList;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactChallengeFragment extends Fragment {

    private ListView contactsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts_challenge, container, false);
        contactsList = (ListView) rootView.findViewById(R.id.contacts_challenge_list);
        setupList();
        return rootView;
    }

    private void setupList() {
        contactsList.setAdapter(createAdapter());
        contactsList.setOnItemClickListener(new ListItemClickListener());
    }

    private ContactChallengeAdapter createAdapter() {
        ArrayList<String> itemsName = new ArrayList<>();
        ArrayList<String> itemsDuration = new ArrayList<>();
        ArrayList<String> itemsKilometers = new ArrayList<>();

        ArrayList<ContactChallenge> contact_challenge = new ArrayList<ContactChallenge>() {{
            add(new ContactChallenge("Anna Hattaway", "Best Time:10'54", "Average:6km"));
            add(new ContactChallenge("Haly Berry", "Best Time:12'28", "Average:4km"));
            add(new ContactChallenge("Will Smith", "Best Time:40'22", "Average:8km"));
            add(new ContactChallenge("Ed Shee", "Best Time:9'42", "Average:6km"));
        }};

        for (int i = 0; i < contact_challenge.size(); i++) {
            itemsName.add(contact_challenge.get(i).challenge_name);
            itemsDuration.add(contact_challenge.get(i).challenge_duration);
            itemsKilometers.add(contact_challenge.get(i).challenge_kilometers_average);
        }

        return new ContactChallengeAdapter(getActivity(), itemsName, itemsDuration, itemsKilometers, new ListItemButtonClickListener(), new ListItemButtonClickListener());
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = contactsList.getFirstVisiblePosition(); i <= contactsList.getLastVisiblePosition(); i++) {
                if (v == contactsList.getChildAt(i - contactsList.getFirstVisiblePosition()).findViewById(R.id.list_item_cc_button_sms)) {
                    Toast.makeText(getActivity(), "Clicked on Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.putExtra("address", "0649153247");
                    smsIntent.putExtra("sms_body","Hello, Do you want to have running together ?");
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



