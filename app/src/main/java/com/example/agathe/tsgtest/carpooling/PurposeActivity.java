package com.example.agathe.tsgtest.carpooling;

import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.dto.CommonTravel;
import com.example.agathe.tsgtest.dto.User;
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

public class PurposeActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static View view;
    private ViewPager mViewPager;
    ArrayList<CommonTravel> travels = new ArrayList<CommonTravel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_carpoolers);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        PageListener pageListener = new PageListener();
        mViewPager.setOnPageChangeListener(pageListener);

        // Give the TabLayout the ViewPager
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("Path #1");
        mTabLayout.getTabAt(1).setText("Path #2");
        mTabLayout.getTabAt(2).setText("Path #");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurposeActivity.this, PotentialCarpoolersActivity.class);
                intent.putExtra("pageNumber", currentPage);
                intent.putParcelableArrayListExtra("travels", travels);
                startActivity(intent);
            }
        });

        // Initialize trajects and users, just for test
        // After, these informations will be provided by API based on NoSQL database which contains all trajects of users
        ArrayList<User> users1 = new ArrayList<User>() {{
            add(new User("Marilyne Beaumont", "closed friend", "0612345678"));
            add(new User("Jean Delaroche", "closed friend", "0612345678"));
            add(new User("Sandra Rouget", "closed work relation", "0612345678"));
            add(new User("Georges Mourin", "known people", "0612345678"));
        }};

        ArrayList<User> users2 = new ArrayList<User>() {{
            add(new User("Jules Noyelles", "closed friend", "0612345678"));
            add(new User("Léa Dallenne", "family relation", "0612345678"));
            add(new User("Caroline Dumoulin", "closed work relation", "0612345678"));
            add(new User("Paul Martin", "known relation", "0612345678"));
        }};

        ArrayList<User> users3 = new ArrayList<User>() {{
            add(new User("Hélèna de Lila", "closed friend", "0612345678"));
            add(new User("Claude Sapin", "closed friend", "0612345678"));
            add(new User("Mélanie Lapin", "closed work relation", "0612345678"));
            add(new User("Hector Sauvage", "known people", "0612345678"));
        }};

        travels.add(new CommonTravel("178 rue Nationale, 59000 LILLE", "195 rue de Paris, 59000 LILLE", new LatLng(50.632752, 3.052427), new LatLng(50.631714, 3.068285),
                users1));
        travels.add(new CommonTravel("178 rue Nationale, 59000 LILLE", "1 Avenue du Pont de Bois, 59650 Villeneuve-d'Ascq", new LatLng(50.632752, 3.052427), new LatLng(50.625480, 3.126518),
                users2));
        travels.add(new CommonTravel("178 rue Nationale, 59000 LILLE", "2 Avenue de la Porte Molitor, 75016 Paris", new LatLng(50.632752, 3.052427), new LatLng(48.833079, 2.265492),
                users3));
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
        private static ArrayList<CommonTravel> travels = new ArrayList<>();

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, ArrayList<CommonTravel> travels) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putParcelableArrayList("travels", travels);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            travels = getArguments().getParcelableArrayList("travels");

            view = inflater.inflate(R.layout.activity_maps, container, false);
            TextView departure = (TextView) view.findViewById(R.id.departure_place);
            TextView destination = (TextView) view.findViewById(R.id.destination_place);
            departure.setText(travels.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).departure);
            destination.setText(travels.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).destination);

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

                    LatLng departure = travels.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).departurePosition;
                    LatLng destination = travels.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).destinationPosition;

                    List<Marker> markers = new ArrayList<Marker>();
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
            });
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
            return new PlaceholderFragment().newInstance(position + 1, travels);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
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