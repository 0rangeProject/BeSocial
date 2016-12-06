package com.example.agathe.tsgtest.carpooling;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.events.CardsAdapter;

import java.util.ArrayList;

/**
 * Created by agathe on 05/12/16.
 */

public class PotentialCarpoolersFragment extends Fragment {
    private ListView cardsList;
    private int pageNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carpoolers, container, false);
        cardsList = (ListView) rootView.findViewById(R.id.cards_list);
        setupList();
        return rootView;
    }

    private void setupList() {
        cardsList.setAdapter(createAdapter());
        cardsList.setOnItemClickListener(new ListItemClickListener());
    }

    private CardsAdapterCarpooling createAdapter() {
        ArrayList<String> itemsName = new ArrayList<String>();
        ArrayList<String> itemsRelation = new ArrayList<String>();
        if (pageNumber == 0) {
            itemsName.add("Mariline Beaumont");
            itemsName.add("Jean Delaroche");
            itemsName.add("Sandra Rouget");
            itemsName.add("Georges Mourin");
            itemsRelation.add("closed friend");
            itemsRelation.add("closed friend");
            itemsRelation.add("closed work relation");
            itemsRelation.add("known people");
        }

        if (pageNumber == 1) {
            itemsName.add("Jules Noyelles");
            itemsName.add("Léa Dallenne");
            itemsName.add("Caroline Dumoulin");
            itemsName.add("Paul Martin");
            itemsRelation.add("closed friend");
            itemsRelation.add("family relation");
            itemsRelation.add("closed work relation");
            itemsRelation.add("known people");
        }

        if (pageNumber == 2) {
            itemsName.add("Hélèna de Lila");
            itemsName.add("Claude Sapin");
            itemsName.add("Mélanie Lapin");
            itemsName.add("Hector Sauvage");
            itemsRelation.add("closed friend");
            itemsRelation.add("closed friend");
            itemsRelation.add("closed work relation");
            itemsRelation.add("known people");
        }

        return new CardsAdapterCarpooling(getActivity(), itemsName, itemsRelation, new ListItemButtonClickListener());
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = cardsList.getFirstVisiblePosition(); i <= cardsList.getLastVisiblePosition(); i++) {
                if (v == cardsList.getChildAt(i - cardsList.getFirstVisiblePosition()).findViewById(R.id.list_item_card_button)) {
                    Toast.makeText(getActivity(), "Clicked on Action Button of List Item " + i, Toast.LENGTH_SHORT).show();
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
