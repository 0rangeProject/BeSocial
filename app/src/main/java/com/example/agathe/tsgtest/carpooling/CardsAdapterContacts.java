package com.example.agathe.tsgtest.carpooling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

import java.util.List;

/**
 * Created by DanchaoGU on 2016/11/8.
 */

public class CardsAdapterContacts extends BaseAdapter {

    private List<String> itemsName;
    private List<String> itemsPhoneNumber;
    private List<String> itemsRelationStrength;
    private final OnClickListener itemButtonClickListener;
    private final Context context;

    public CardsAdapterContacts(Context context, List<String> itemsName, List<String> itemsPhoneNumber, List<String> itemsRelationStrength, OnClickListener itemButtonClickListener) {
        this.context = context;
        this.itemsName = itemsName;
        this.itemsPhoneNumber = itemsPhoneNumber;
        this.itemsRelationStrength = itemsRelationStrength;
        this.itemButtonClickListener = itemButtonClickListener;
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

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_card_contacts, null);

            holder = new ViewHolder();
            holder.itemName = (TextView) convertView.findViewById(R.id.name);
            holder.itemPhoneNumber = (TextView) convertView.findViewById(R.id.phone_number);
            holder.itemRelationStrength = (TextView) convertView.findViewById(R.id.relation_strength);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(itemsName.get(position));
        holder.itemPhoneNumber.setText(itemsPhoneNumber.get(position));
        holder.itemRelationStrength.setText(itemsRelationStrength.get(position));

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemName;
        private TextView itemPhoneNumber;
        private TextView itemRelationStrength;
    }

}
