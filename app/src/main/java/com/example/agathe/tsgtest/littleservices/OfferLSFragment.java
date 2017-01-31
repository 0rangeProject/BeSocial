package com.example.agathe.tsgtest.littleservices;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.OfferLS;

import java.util.ArrayList;

/**
 * Created by koudm on 25/01/2017.
 */

public class OfferLSFragment extends Fragment {
    private ListView offersList;
    private FloatingActionButton publish_offer_btn;
    public OfferLSFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_little_services, container, false);
        offersList = (ListView) rootView.findViewById(R.id.offers_ls_list);
        publish_offer_btn = (FloatingActionButton) rootView.findViewById(R.id.f_btn_publish_offer);
        publish_offer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PublishOfferActivity.class);
                startActivity(intent);
            }
        });
        setupList();
        return rootView;
    }

    private void setupList() {
        offersList.setAdapter(createAdapter());
        offersList.setOnItemClickListener(new ListItemClickListener());
    }

    private OfferLSAdapter createAdapter(){
        ArrayList<String> itemsTitle = new ArrayList<>();
        ArrayList<String> itemsType = new ArrayList<>();
        ArrayList<String> itemsDescription = new ArrayList<>();
        ArrayList<String> itemsLocation = new ArrayList<>();

        ArrayList<OfferLS> offer_ls = new ArrayList<OfferLS>() {{
            add(new OfferLS("Teen doing good with under 10years old children", "Babysitting", "I'm a philo student", "around 10km"));
            add(new OfferLS("IT student can help in high school level maths", "Home Teaching", "I'm every wednesday afternoons", "around 50km"));
            add(new OfferLS("Screwdriver for repairs", "Tools","I have a new amazing screwdriver I do not use twice a week", "around 300km"));
            add(new OfferLS("Man that can shop for elders twice a week", "Shop", "I visit my grandma in a village","around 200km"));;
        }};

        for (int i = 0; i < offer_ls.size(); i++) {
            itemsTitle.add(offer_ls.get(i).offer_title);
            itemsType.add(offer_ls.get(i).offer_type);
            itemsDescription.add(offer_ls.get(i).offer_description);
            itemsLocation.add(offer_ls.get(i).location);
        }

        return new OfferLSAdapter(getActivity(),itemsTitle, itemsType, itemsDescription, itemsLocation, new ListItemButtonClickListener());
    }
    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = offersList.getFirstVisiblePosition(); i <= offersList.getLastVisiblePosition(); i++) {
                //sms intent
                if (v == offersList.getChildAt(i - offersList.getFirstVisiblePosition()).findViewById(R.id.list_item_cc_button_sms)) {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.putExtra("address", "0649153247");
                    smsIntent.putExtra("sms_body", "Hello, I'm interrested by your offer");
                    smsIntent.setData(Uri.parse("smsto:" + "0649153247"));
                    startActivity(smsIntent);
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
