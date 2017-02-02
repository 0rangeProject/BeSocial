package com.example.agathe.tsgtest.littleservices;

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
 * Created by koudm on 26/01/2017.
 */

public class OfferLSAdapter extends BaseAdapter {
    private List<String> itemsTitle;
    private List<String> itemsType;
    private List<String> itemsDescription;
    private List<String> itemsLocation;
    private final View.OnClickListener itemButtonSmsClickListener;
    private final Context context;

    public OfferLSAdapter(Context context, List<String> itemsTitle, List<String> itemsType,List<String> itemsDescription, List<String> itemsLocation, View.OnClickListener itemButtonSmsClickListener) {
        this.context = context;
        this.itemsTitle = itemsTitle;
        this.itemsType = itemsType;
        this.itemsDescription = itemsDescription;
        this.itemsLocation = itemsLocation;
        this.itemButtonSmsClickListener = itemButtonSmsClickListener;
    }

    @Override
    public int getCount() {
        return itemsTitle.size();
    }

    @Override
    public Object getItem(int i) { return itemsTitle.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OfferViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_offers_little_services, null);

            holder = new OfferViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.offer_title_ls);
            holder.itemType = (TextView) convertView.findViewById(R.id.offer_type_ls);
            holder.itemDescription = (TextView) convertView.findViewById(R.id.offer_description_ls);
            holder.itemLocation = (TextView) convertView.findViewById(R.id.contact_location);
            holder.itemSmsButton = (Button) convertView.findViewById(R.id.offer_ls_send_sms_btn);
            convertView.setTag(holder);

        } else {
            holder = (OfferViewHolder) convertView.getTag();
        }

        holder.itemTitle.setText(itemsTitle.get(position));
        holder.itemType.setText(itemsType.get(position));
        holder.itemDescription.setText(itemsDescription.get(position));
        holder.itemLocation.setText(itemsLocation.get(position));

        if (itemButtonSmsClickListener != null) {
            holder.itemSmsButton.setOnClickListener(itemButtonSmsClickListener);
        }

        return convertView;
    }

    private static class OfferViewHolder {
        private TextView itemTitle, itemType, itemDescription, itemLocation;
        private Button itemSmsButton;
    }
}
