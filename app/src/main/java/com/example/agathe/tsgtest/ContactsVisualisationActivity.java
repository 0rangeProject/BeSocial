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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_entries_visualisation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_manual_entry_2);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        String[] tokens = {"116f8bff-950f-4e63-ae5e-eaeff8f10566",
                "239e0b68-9bde-4718-926a-26cd339860e9"};

        ContactsTask ct = new ContactsTask(ContactsVisualisationActivity.this, tokens);
        ct.getContactsList(new ContactsTask.VolleyCallback(){
            @Override
            public void onSuccess(List<List<Contact>> contacts) {

                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), "PREFERENCES_FILE", MODE_PRIVATE);
                for (List<Contact> subContacts : contacts) {
                    ListContacts list = new ListContacts();
                    list.setContacts(subContacts);
                    complexPreferences.putObject(subContacts.get(0).getName(), list);
                }
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

                mTabLayout.getTabAt(0).setText("Frequent contacts");
                mTabLayout.getTabAt(1).setText("Business contacts");
                mTabLayout.getTabAt(2).setText("Private contacts");
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
        private Context context;
        private ListView cardsList;
        List<List<Contact>> contacts = new ArrayList<>();

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final List<String> types = new ArrayList<>();
            types.add("frequent_contacts");
            types.add("business_contacts");
            types.add("private_contacts");

            List<Contact> subContacts;

            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getContext(), "PREFERENCES_FILE", MODE_PRIVATE);
            for (String s : types) {
                ListContacts complexObject = complexPreferences.getObject(s, ListContacts.class);
                if (complexObject != null) {
                    subContacts = complexObject.getContacts();
                    contacts.add(subContacts);
                }
            }

            view = inflater.inflate(R.layout.fragment_contacts, container, false);
            cardsList = (ListView) view.findViewById(R.id.cards_list_contacts);
            setupList(getArguments().getInt(ARG_SECTION_NUMBER));

            return view;
        }

        private void setupList(int pageNumber) {
            cardsList.setAdapter(createAdapter(pageNumber));
        }

        private CardsAdapterContacts createAdapter(int pageNumber) {
            ArrayList<String> itemsName = new ArrayList<>();
            ArrayList<String> itemsPhoneNumber= new ArrayList<>();
            ArrayList<String> itemsRelationStrength = new ArrayList<>();

            for (int i = 1; i < contacts.get(pageNumber - 1).size(); i++) {
                itemsName.add(contacts.get(pageNumber - 1).get(i).getName());
                itemsPhoneNumber.add(contacts.get(pageNumber - 1).get(i).getPhoneNumber());
                itemsRelationStrength.add(contacts.get(pageNumber - 1).get(i).getRelationStrength());
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

