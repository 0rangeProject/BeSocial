package com.example.agathe.tsgtest.littleservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.agathe.tsgtest.R;
import com.example.agathe.tsgtest.SettingsActivity;
import com.olab.smplibrary.SMPLibrary;


/**
 * Created by koudm on 30/10/2016.
 */

public class LittleServicesActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setup layout and its elements
        setContentView(R.layout.activity_little_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_little_services);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Little Services"); //because it displays "Be social"

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //Set tabs Toolbar
        TabLayout tabs = (TabLayout) findViewById(R.id.my_toolbar_tabs_little_services);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(viewPager);

    }
     private void setupViewPager(ViewPager viewPager) {
         MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
         viewPager.setAdapter(adapter);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {
         MyViewPagerAdapter(FragmentManager manager) { super(manager); }

        @Override
        public Fragment getItem(int position) {
// return each fragment tabs
            switch (position) {
                case 0:
                    return  new AskingLSFragment();
                case 1:
                    return new OfferLSFragment();
                case 2:
                    return new RecommendationLSFragment();
                default:
                    return new AskingLSFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;// there are only 3tabs :)
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return  "Ask for a service";
                case 1:
                    return "Suggestions";
                case 2:
                    return "Recommended";
                default:
                    return "Ask for a service";
            }
           // return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  super.onCreateOptionsMenu(menu);
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
                Intent intent = new Intent(LittleServicesActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
