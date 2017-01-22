package com.example.agathe.tsgtest.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;
import com.olab.smplibrary.SMPLibrary;

public class EventDetailActivity extends AppCompatActivity {
    private ImageView event_detail_list_item_img;
    private TextView event_detail_list_item_title, event_detail_list_item_time,
            event_detail_list_item_place, event_detail_list_item_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        event_detail_list_item_img = (ImageView) findViewById(R.id.event_detail_list_item_img);
        event_detail_list_item_title = (TextView) findViewById(R.id.event_detail_list_item_title);
        event_detail_list_item_time = (TextView) findViewById(R.id.event_detail_list_item_time);
        event_detail_list_item_place = (TextView) findViewById(R.id.event_detail_list_item_place);
        event_detail_list_item_context = (TextView) findViewById(R.id.event_detail_list_item_context);

        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_ed);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //通过Activity.getIntent()获取当前页面接收到的Intent。
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        ListItem event = (ListItem) intent.getSerializableExtra("Events");
        int img_src_id = getResources().getIdentifier(event.getImage(), "drawable", getPackageName());
        event_detail_list_item_img.setImageResource(img_src_id);
        event_detail_list_item_title.setText(event.getTitle());
        event_detail_list_item_time.setText(event.getTime());
        event_detail_list_item_place.setText(event.getPlace());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_public_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:

                return true;

            case R.id.action_interested:
                SMPLibrary.Logout();
                return true;

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

    private void refresh() {
        finish();
        Intent intent = new Intent(EventDetailActivity.this, PublicEventsActivity.class);
        startActivity(intent);
    }
}
