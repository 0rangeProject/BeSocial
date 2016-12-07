package com.example.agathe.tsgtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactLSAdapter extends ArrayAdapter<ContactLS> {

    //challenge_contacts est la liste des models de contact à afficher
    public ContactLSAdapter(Context context, List<ContactLS> ls_contacts) {
        super(context, 0, ls_contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_ls_item,parent, false);
        }

       ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ContactViewHolder();
            viewHolder.ls_name = (TextView) convertView.findViewById(R.id.contact_ls_name);
            viewHolder.location = (TextView) convertView.findViewById(R.id.contact_location);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ContactLS contactLS = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.ls_name.setText(contactLS.getLSName());
        viewHolder.location.setText(contactLS.getLocation());

        return convertView;
    }

    public class ContactViewHolder{
        public TextView ls_name, location;
    }
}