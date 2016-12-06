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

public class CardsAdapterCarpooling extends BaseAdapter {

    private List<String> itemsName;
    private List<String> itemsRelation;
    private final OnClickListener itemButtonClickListener;
    private final Context context;

    public CardsAdapterCarpooling(Context context, List<String> itemsName, List<String> itemsRelation, OnClickListener itemButtonClickListener) {
        this.context = context;
        this.itemsName = itemsName;
        this.itemsRelation = itemsRelation;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_card_carpooling, null);

            holder = new ViewHolder();
            holder.itemName = (TextView) convertView.findViewById(R.id.name);
            holder.itemRelation = (TextView) convertView.findViewById(R.id.type_of_relation);
            holder.itemButton = (Button) convertView.findViewById(R.id.list_item_card_button);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(itemsName.get(position));
        holder.itemRelation.setText(itemsRelation.get(position));

        if (itemButtonClickListener != null) {
            holder.itemButton.setOnClickListener(itemButtonClickListener);
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemName;
        private TextView itemRelation;
        private Button itemButton;
    }

}
