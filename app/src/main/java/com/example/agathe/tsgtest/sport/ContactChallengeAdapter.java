package com.example.agathe.tsgtest.sport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

import java.util.List;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactChallengeAdapter extends ArrayAdapter<ContactChallenge> {

    //challenge_contacts est la liste des models de contact à afficher
    public ContactChallengeAdapter(Context context, List<ContactChallenge> challenge_contacts) {
        super(context, 0, challenge_contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_challenge_item,parent, false);
        }

       ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ContactViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.contact_name);
            viewHolder.duration = (TextView) convertView.findViewById(R.id.contact_run_duration);
            viewHolder.km_average = (TextView) convertView.findViewById(R.id.contact_kilometers_average);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.img_avatar);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ContactChallenge contactChallenge = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(contactChallenge.getName());
        viewHolder.duration.setText(contactChallenge.getDuration());

        return convertView;
    }




    public class ContactViewHolder{
        public TextView name, duration, km_average;
        public ImageView avatar;
        public ImageButton btn_sms, btn_call;
    }
}