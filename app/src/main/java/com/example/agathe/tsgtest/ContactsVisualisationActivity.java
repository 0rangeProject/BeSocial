package com.example.agathe.tsgtest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agathe.tsgtest.carpooling.CardsAdapterCarpooling;
import com.example.agathe.tsgtest.carpooling.CardsAdapterContacts;
import com.example.agathe.tsgtest.carpooling.ListContacts;
import com.example.agathe.tsgtest.carpooling.ListTravels;
import com.example.agathe.tsgtest.carpooling.NewTripDialogFragment;
import com.example.agathe.tsgtest.carpooling.PotentialCarpoolersFragment;
import com.example.agathe.tsgtest.dto.CommonTravel;
import com.example.agathe.tsgtest.dto.Contact;
import com.example.agathe.tsgtest.dto.ManualTrip;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 06/02/17.
 */

public class ContactsVisualisationActivity extends AppCompatActivity{

    private static final String LOG_TAG = "ContactsVisualisation";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static View view;
    private ViewPager mViewPager;

    private ListView cardsList;
    private int pageNumber = 0;
    private ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_entries_visualisation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_manual_entry_2);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        String[] tokens = {"3ad3cbd4-75e2-4078-9721-391c8ec8ff56",
                "166a8660-18e7-41fa-a895-7ddb02db4796"};

        ContactsTask ct = new ContactsTask(ContactsVisualisationActivity.this, tokens);
        ct.getContactsList("frequent_contacts",
                new ContactsTask.VolleyCallback(){
            @Override
            public void onSuccess(List<Contact> contacts) {
                ListContacts list = new ListContacts();
                list.setContacts(contacts);

                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), "PREFERENCES_FILE", MODE_PRIVATE);
                complexPreferences.putObject("contacts1", list);
                complexPreferences.putObject("contacts2", list);
                complexPreferences.putObject("contacts3", list);
                complexPreferences.commit();

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container_2);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                PageListener pageListener = new PageListener();
                mViewPager.setOnPageChangeListener(pageListener);

                // Give the TabLayout the ViewPager
                TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs_2);
                mTabLayout.setupWithViewPager(mViewPager);

                mTabLayout.getTabAt(0).setText("Best Friends");
                mTabLayout.getTabAt(1).setText("Friends");
                mTabLayout.getTabAt(2).setText("Known people");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                builder.setPositiveButton(R.string.dialog_ok, null);
                builder.setIcon(R.mipmap.ic_launcher);

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_main_settings:
                Intent intent = new Intent(ContactsVisualisationActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int pageNumber;
        private Context context;
        private List<Contact> contacts1 = new ArrayList<>();
        private List<Contact> contacts2 = new ArrayList<>();
        private List<Contact> contacts3 = new ArrayList<>();
        private ListView cardsList;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            pageNumber = sectionNumber;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getContext(), "PREFERENCES_FILE", MODE_PRIVATE);
            ListContacts complexObject1 = complexPreferences.getObject("contacts1", ListContacts.class);
            ListContacts complexObject2 = complexPreferences.getObject("contacts2", ListContacts.class);
            ListContacts complexObject3 = complexPreferences.getObject("contacts3", ListContacts.class);

            if (complexObject1 != null) {
                contacts1 = complexObject1.getContacts();
            }
            if (complexObject2 != null) {
                contacts2 = complexObject2.getContacts();
            }
            if (complexObject3 != null) {
                contacts3 = complexObject3.getContacts();
            }

            view = inflater.inflate(R.layout.fragment_contacts, container, false);
            cardsList = (ListView) view.findViewById(R.id.cards_list_contacts);
            setupList();

            return view;
        }

        private void setupList() {
            cardsList.setAdapter(createAdapter());
        }

        private CardsAdapterContacts createAdapter() {
            ArrayList<String> itemsName = new ArrayList<>();
            ArrayList<String> itemsPhoneNumber= new ArrayList<>();
            ArrayList<String> itemsRelationStrength = new ArrayList<>();

            if (pageNumber == 1) {
                for (int i = 0; i < contacts1.size(); i++) {
                    itemsName.add(contacts1.get(i).getName());
                    itemsPhoneNumber.add(contacts1.get(i).getPhoneNumber());
                    itemsRelationStrength.add(contacts1.get(i).getRelationStrength());
                }
            }
            if (pageNumber == 2) {
                for (int i = 0; i < contacts2.size(); i++) {
                    itemsName.add(contacts2.get(i).getName());
                    itemsPhoneNumber.add(contacts2.get(i).getPhoneNumber());
                    itemsRelationStrength.add(contacts2.get(i).getRelationStrength());
                }
            }
            if (pageNumber == 3) {
                for (int i = 0; i < contacts3.size(); i++) {
                    itemsName.add(contacts3.get(i).getName());
                    itemsPhoneNumber.add(contacts3.get(i).getPhoneNumber());
                    itemsRelationStrength.add(contacts3.get(i).getRelationStrength());
                }
            }

            return new CardsAdapterContacts(getActivity(), itemsName, itemsPhoneNumber, itemsRelationStrength, new ListItemButtonClickListener());
        }

        private final class ListItemButtonClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {

            }
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.i(LOG_TAG, String.valueOf(position));
            return new PlaceholderFragment().newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private static int currentPage;

    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            currentPage = position;
        }
    }
}

