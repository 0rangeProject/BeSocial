package com.example.agathe.tsgtest.events;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

/**
 * Created by DanchaoGU on 2016/11/8.
 */

public class EventListAdapter extends BaseAdapter {

    private List<ListItem> items;
    private final OnClickListener itemButtonClickListener;
    private final Context context;

    public EventListAdapter(Context context, List<ListItem> items, OnClickListener itemButtonClickListener) {
        this.context = context;
        this.items = items;
        this.itemButtonClickListener = itemButtonClickListener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ListItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list_item, null);

            holder = new ViewHolder();
            holder.itemImg = (ImageView) convertView.findViewById(R.id.event_list_item_img);
            holder.itemTitle = (TextView) convertView.findViewById(R.id.event_list_item_title);
            holder.itemTime = (TextView) convertView.findViewById(R.id.event_list_item_time);
            holder.itemPlace = (TextView) convertView.findViewById(R.id.event_list_item_place);
            holder.itemButton1 = (Button) convertView.findViewById(R.id.event_list_item_button_1);
            holder.itemButton2 = (Button) convertView.findViewById(R.id.event_list_item_button_2);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTitle.setText(items.get(position).getTitle());
        holder.itemTime.setText(items.get(position).getTime());
        holder.itemPlace.setText(items.get(position).getPlace());
        int img_src_id = context.getResources().getIdentifier(items.get(position).getImage(), "drawable", context.getPackageName());
        holder.itemImg.setImageResource(img_src_id);
        Log.i("EventListAdapter", "img_scr_id " + img_src_id);
        if (itemButtonClickListener != null) {
            holder.itemButton1.setOnClickListener(itemButtonClickListener);
            holder.itemButton2.setOnClickListener(itemButtonClickListener);
        }

        return convertView;
    }

    private static class ViewHolder {
        private ImageView itemImg;
        private TextView itemTitle;
        private TextView itemTime;
        private TextView itemPlace;
        private Button itemButton1;
        private Button itemButton2;
    }

}

class ListItem{
    private String list_title;
    private String list_time;
    private String list_place;
    private String list_img;
    private boolean isInterested;

    public ListItem(String title, String time, String place, String image_name, boolean isInterested){
        this.list_title = title;
        this.list_time = time;
        this.list_place = place;
        this.list_img = image_name;
        this.isInterested = isInterested;
    }
    public String getTitle(){return list_title;}
    public String getTime(){return list_time;}
    public String getPlace(){return list_place;}
    public String getImage(){return list_img;}
    public boolean isInterested(){return isInterested;}

}