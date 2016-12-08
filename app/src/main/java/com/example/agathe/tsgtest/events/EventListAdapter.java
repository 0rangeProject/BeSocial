package com.example.agathe.tsgtest.events;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agathe.tsgtest.R;

/**
 * Created by DanchaoGU on 2016/11/8.
 */

public class EventListAdapter extends RecyclerView.Adapter {
    public interface OnRecyclerViewListener {
        void onItemClick(int position);
        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = EventListAdapter.class.getSimpleName();
    private List<ListItem> items;
    private final Context context;

    public EventListAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder, i: " + i);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder, i: " + i + ", viewHolder: " + viewHolder);
        EventViewHolder holder = (EventViewHolder) viewHolder;
        holder.position = i;
        holder.itemTitle.setText(items.get(i).getTitle());
        holder.itemTitle.setBackgroundColor(Color.argb(50, 0, 0, 0));
        holder.itemTime.setText(items.get(i).getTime());
        holder.itemPlace.setText(items.get(i).getPlace());
        int img_src_id = context.getResources().getIdentifier(items.get(i).getImage(), "drawable", context.getPackageName());
        holder.itemImg.setImageResource(img_src_id);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        public View rootView;
        private ImageView itemImg;
        private TextView itemTitle;
        private TextView itemTime;
        private TextView itemPlace;
        private Button itemButton1;
        private Button itemButton2;
        public int position;

        public EventViewHolder(View itemView) {
            super(itemView);
            itemImg = (ImageView) itemView.findViewById(R.id.event_list_item_img);
            itemTitle = (TextView) itemView.findViewById(R.id.event_list_item_title);
            itemTime = (TextView) itemView.findViewById(R.id.event_list_item_time);
            itemPlace = (TextView) itemView.findViewById(R.id.event_list_item_place);
            itemButton1 = (Button) itemView.findViewById(R.id.event_list_item_button_1);
            itemButton2 = (Button) itemView.findViewById(R.id.event_list_item_button_2);
            rootView = itemView.findViewById(R.id.event_list_CardView);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(null != onRecyclerViewListener){
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
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