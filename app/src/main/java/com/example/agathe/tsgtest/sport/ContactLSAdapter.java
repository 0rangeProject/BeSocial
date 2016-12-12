package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

import java.util.List;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactLSAdapter extends BaseAdapter {

    private List<String> itemsName;
    private List<String> itemsLocation;
    private final View.OnClickListener itemButtonSmsClickListener,itemButtonCallClickListener;
    private final Context context;

    public ContactLSAdapter(Context context, List<String> itemsName, List<String> itemsLocation, View.OnClickListener itemButtonSmsClickListener, View.OnClickListener itemButtonCallClickListener) {
        this.context = context;
        this.itemsName = itemsName;
        this.itemsLocation = itemsLocation;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_contacts_little_services, null);

            holder = new ContactViewHolder();
            holder.itemName = (TextView) convertView.findViewById(R.id.contact_ls_name);
            holder.itemLocation = (TextView) convertView.findViewById(R.id.contact_location);
            holder.itemSmsButton = (Button) convertView.findViewById(R.id.list_item_cc_button_sms);
            holder.itemCallButton = (Button) convertView.findViewById(R.id.list_item_cc_button_call);
            convertView.setTag(holder);

        } else {
            holder = (ContactViewHolder) convertView.getTag();
        }

        holder.itemName.setText(itemsName.get(position));
        holder.itemLocation.setText(itemsLocation.get(position));

        if (itemButtonSmsClickListener != null) {
            holder.itemSmsButton.setOnClickListener(itemButtonSmsClickListener);
        }

        if (itemButtonCallClickListener != null) {
            holder.itemCallButton.setOnClickListener(itemButtonCallClickListener);
        }


        return convertView;
    }

    private static class ContactViewHolder {
        private TextView itemName, itemLocation;
        private Button itemSmsButton, itemCallButton;
    }
}
