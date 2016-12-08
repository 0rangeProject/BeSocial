package com.example.agathe.tsgtest.carpooling;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.CommonTravel;
import com.example.agathe.tsgtest.dto.User;
import com.example.agathe.tsgtest.events.CardsAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 05/12/16.
 */

public class PotentialCarpoolersFragment extends Fragment {

    private ListView cardsList;
    private int pageNumber = 0;
    private ArrayList<CommonTravel> travels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carpoolers, container, false);
        cardsList = (ListView) rootView.findViewById(R.id.cards_list);
        pageNumber = getArguments().getInt("pageNumber");
        travels = getArguments().getParcelableArrayList("travels");
        setupList();
        //TextView textView = (TextView) rootView;
        //textView.setText("Path #" + pageNumber);
        return rootView;
    }

    private void setupList() {
        cardsList.setAdapter(createAdapter());
        cardsList.setOnItemClickListener(new ListItemClickListener());
    }

    private CardsAdapterCarpooling createAdapter() {
        ArrayList<String> itemsName = new ArrayList<>();
        ArrayList<String> itemsRelation = new ArrayList<>();

        for (int i = 0; i < travels.size(); i++) {
            itemsName.add(travels.get(pageNumber).users.get(i).name);
            itemsRelation.add(travels.get(pageNumber).users.get(i).relation);
        }

        return new CardsAdapterCarpooling(getActivity(), itemsName, itemsRelation, new ListItemButtonClickListener());
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = cardsList.getFirstVisiblePosition(); i <= cardsList.getLastVisiblePosition(); i++) {
                if (v == cardsList.getChildAt(i - cardsList.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button)) {
                    Toast.makeText(getActivity(), "Clicked on Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    String phoneNumber = travels.get(pageNumber).users.get(i).phoneNumber;
                    smsIntent.putExtra("address", phoneNumber);
                    smsIntent.putExtra("sms_body","Hello, I saw thatwe often do the same trajects, from "
                            + travels.get(pageNumber).departure + " to " + travels.get(pageNumber).destination + ". Are you interested by carpooling together ?");
                    smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
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
