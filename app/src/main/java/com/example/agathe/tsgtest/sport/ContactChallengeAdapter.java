package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

import java.util.List;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactChallengeAdapter extends BaseAdapter {

    private List<String> itemsName;
    private List<String> itemsDuration;
    private List<String> itemsKilometers;
    private final View.OnClickListener itemButtonSmsClickListener,itemButtonCallClickListener;
    private final Context context;

    public ContactChallengeAdapter(Context context, List<String> itemsName, List<String> itemsDuration, List<String> itemsKilometers, View.OnClickListener itemButtonSmsClickListener, View.OnClickListener itemButtonCallClickListener) {
        this.context = context;
        this.itemsName = itemsName;
        this.itemsDuration = itemsDuration;
        this.itemsKilometers = itemsKilometers;
        this.itemButtonSmsClickListener = itemButtonSmsClickListener;
        this.itemButtonCallClickListener = itemButtonCallClickListener;
    }

    @Override
    public int getCount() {
        return itemsName.size();
    }

    @Override
    public String getItem(int position) {
        return itemsName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_contacts_challenge, null);

            holder = new ContactViewHolder();
            holder.itemName = (TextView) convertView.findViewById(R.id.contact_name);
            holder.itemDuration = (TextView) convertView.findViewById(R.id.contact_run_duration);
            holder.itemKilometers = (TextView) convertView.findViewById(R.id.contact_kilometers_average);
            holder.itemSmsButton = (Button) convertView.findViewById(R.id.list_item_cc_button_sms);
            holder.itemCallButton = (Button) convertView.findViewById(R.id.list_item_cc_button_call);
            convertView.setTag(holder);

        } else {
            holder = (ContactViewHolder) convertView.getTag();
        }

        holder.itemName.setText(itemsName.get(position));
        holder.itemDuration.setText(itemsDuration.get(position));
        holder.itemKilometers.setText(itemsKilometers.get(position));

        if (itemButtonSmsClickListener != null) {
            holder.itemSmsButton.setOnClickListener(itemButtonSmsClickListener);
        }

        if (itemButtonCallClickListener != null) {
            holder.itemCallButton.setOnClickListener(itemButtonCallClickListener);
        }


        return convertView;
    }

    private static class ContactViewHolder {
        private TextView itemName, itemDuration, itemKilometers;
        private Button itemSmsButton, itemCallButton;
    }
}

