package com.example.agathe.tsgtest.littleservices;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.RecommendationLS;

import java.util.ArrayList;

/**
 * Created by koudm on 25/01/2017.
 */

public class RecommendationLSFragment extends Fragment {
    private ListView recommendedList;
    private RatingBar rating;
    public RecommendationLSFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recommendation_little_services, container, false);
        recommendedList = (ListView) rootView.findViewById(R.id.recommendations_list);
        rating = (RatingBar) rootView.findViewById(R.id.rating_recommendation_ls);
        setupList();
        //rating.setRating(4);
        return rootView;
    }

    private void setupList() {
        recommendedList.setAdapter(createAdapter());
    }

    private RecommendationLSAdapter createAdapter(){
        ArrayList<String> itemsName = new ArrayList<>();
        ArrayList<String> itemsLocation = new ArrayList<>();
        ArrayList<String> itemsRecommendationType = new ArrayList<>();

        ArrayList<RecommendationLS> recommendations = new ArrayList<RecommendationLS>() {{
            add(new RecommendationLS("Julie", "around 10km", "Home teaching"));
            add(new RecommendationLS("Paul", "around 50km", "Shop for elders"));
            add(new RecommendationLS("Claire", "around 300km", "Babysitting"));
        }};

        for (int i = 0; i < recommendations.size(); i++) {
            itemsName.add(recommendations.get(i).contact_name);
            itemsLocation.add(recommendations.get(i).location);
            itemsRecommendationType.add(recommendations.get(i).recommendation_type);
        }

        return new RecommendationLSAdapter(getActivity(),itemsName, itemsLocation, itemsRecommendationType, new ListItemButtonClickListener(), new ListItemButtonClickListener());
    }
    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = recommendedList.getFirstVisiblePosition(); i <= recommendedList.getLastVisiblePosition(); i++) {
                //sms intent
                if (v == recommendedList.getChildAt(i - recommendedList.getFirstVisiblePosition()).findViewById(R.id.list_item_recommended_button_sms)) {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.putExtra("address", "0649153247");
                    smsIntent.putExtra("sms_body", "Hello, I'm interrested by your offer");
                    smsIntent.setData(Uri.parse("smsto:" + "0649153247"));
                    startActivity(smsIntent);
                }
                //calling intent
                if (v == recommendedList.getChildAt(i - recommendedList.getFirstVisiblePosition()).findViewById(R.id.list_item_recommended_button_call)) {
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
