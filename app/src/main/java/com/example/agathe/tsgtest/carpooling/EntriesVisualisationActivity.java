package com.example.agathe.tsgtest.carpooling;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agathe.tsgtest.ComplexPreferences;
import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.CommonTravel;
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
import com.olab.smplibrary.SMPLibrary;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 18/12/16.
 */

public class EntriesVisualisationActivity extends AppCompatActivity {

    private static final String LOG_TAG = "EntriesVisualisationA";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static View view;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_visualisation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_manual_entry);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        List<ManualTrip> manualEntries = new ArrayList<>();
        ManualTrip mt = new ManualTrip("178 rue Nationale, 59000 LILLE", "195 rue de Paris, 59000 LILLE", new LatLng(50.632752, 3.052427), new LatLng(50.631714, 3.068285));
        manualEntries.add(0, mt);
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), "PREFERENCES_FILE", MODE_PRIVATE);

        ListTravels list = new ListTravels();
        list.setTravels(manualEntries);
        complexPreferences.putObject("list", list);
        complexPreferences.commit();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        PageListener pageListener = new PageListener();
        mViewPager.setOnPageChangeListener(pageListener);

        // Give the TabLayout the ViewPager
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            mTabLayout.getTabAt(i).setText("Path #" + String.valueOf(i + 1));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                NewTripDialogFragment newFragment = new NewTripDialogFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current page and delete

                List<ManualTrip> manualEntries = new ArrayList<>();
                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), "PREFERENCES_FILE", MODE_PRIVATE);
                ListTravels complexObject = complexPreferences.getObject("list", ListTravels.class);
                if (complexObject != null) {
                    manualEntries = complexObject.getTravels();
                }
                manualEntries.remove(currentPage);
                if (manualEntries.size() != 0) {

                } else {
                    manualEntries.add(0, null);
                }

                ListTravels list = new ListTravels();
                list.setTravels(manualEntries);
                complexPreferences.putObject("list", list);
                complexPreferences.commit();

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                PageListener pageListener = new PageListener();
                mViewPager.setOnPageChangeListener(pageListener);

                // Give the TabLayout the ViewPager
                TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                mTabLayout.setupWithViewPager(mViewPager);
                for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                    mTabLayout.getTabAt(i).setText("Path #" + String.valueOf(i + 1));
                }




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
            case R.id.action_disconnect:
                SMPLibrary.Logout();
                return true;

            case R.id.action_about:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.app_name);
                builder.setPositiveButton(R.string.dialog_ok, null);
                builder.setIcon(R.mipmap.ic_launcher);

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case R.id.action_main_settings:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
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
        private List<ManualTrip> manualEntries = new ArrayList<>();
        private Context context;

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            context = getContext();
            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, "PREFERENCES_FILE", MODE_PRIVATE);
            ListTravels complexObject = complexPreferences.getObject("list", ListTravels.class);

            if (complexObject != null) {
                manualEntries = complexObject.getTravels();
            }

            view = inflater.inflate(R.layout.activity_maps_manual, container, false);
            TextView departure = (TextView) view.findViewById(R.id.departure_place);
            TextView destination = (TextView) view.findViewById(R.id.destination_place);
            departure.setText(manualEntries.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).departure);
            destination.setText(manualEntries.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).destination);

            return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            FragmentManager fm = getChildFragmentManager();
            SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
            if (mapFragment == null) {
                mapFragment = new SupportMapFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
                ft.commit();
                fm.executePendingTransactions();
            }

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    GoogleMap mMap = googleMap;

                    LatLng departure = manualEntries.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).departurePosition;
                    LatLng destination = manualEntries.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).destinationPosition;

                    List<Marker> markers = new ArrayList<Marker>();
                    if (departure != null && destination != null) {
                        markers.add(mMap.addMarker(new MarkerOptions().position(departure).title("Departure")));
                        markers.add(mMap.addMarker(new MarkerOptions().position(destination)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .title("Destination")));

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (Marker marker : markers) {
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds bounds = builder.build();

                        int padding = 30; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                        googleMap.moveCamera(cu);
                    }
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<ManualTrip> manualEntries = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            // Récupérer les informations stockées manuellement pr l'utilisateur
            ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(getApplicationContext(), "PREFERENCES_FILE", MODE_PRIVATE);
            ListTravels complexObject = complexPreferences.getObject("list", ListTravels.class);

            if (complexObject != null) {
                manualEntries = complexObject.getTravels();
            }

            if (manualEntries.get(0) == null) {
                manualEntries.remove(0);
            }
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new PlaceholderFragment().newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return manualEntries.size();
        }
    }

    private static int currentPage;

    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            currentPage = position;
        }
    }
}