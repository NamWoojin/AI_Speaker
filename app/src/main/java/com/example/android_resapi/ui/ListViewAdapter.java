package com.example.android_resapi.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_resapi.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_MEDICINE = 0 ;
    private static final int ITEM_VIEW_TYPE_MEAL = 1 ;
    private static final int ITEM_VIEW_TYPE_SLEEP = 2;
    private static final int ITEM_VIEW_TYPE_BEAT = 3;
    private static final int ITEM_VIEW_TYPE_MAX = 4 ;

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;


    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX ;
    }

    @Override
    public int getItemViewType(int position) {
        return listViewItemList.get(position).getType() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            ListViewItem listViewItem = listViewItemList.get(position);

            switch (viewType) {
                case ITEM_VIEW_TYPE_MEDICINE:
                    convertView = inflater.inflate(R.layout.healthinfo_medicine, parent, false);


                    break;
                case ITEM_VIEW_TYPE_MEAL:
                    convertView = inflater.inflate(R.layout.healthinfo_meal,parent, false);

                    ImageView morningImageView = (ImageView) convertView.findViewById(R.id.meal_morning_id) ;
                    ImageView afternoonImageView = (ImageView) convertView.findViewById(R.id.meal_afternoon_id) ;
                    ImageView nightImageView = (ImageView) convertView.findViewById(R.id.meal_night_id) ;

                    morningImageView.setImageDrawable(listViewItem.getMorningImage());
                    afternoonImageView.setImageDrawable(listViewItem.getAfternoonImage());
                    nightImageView.setImageDrawable(listViewItem.getNightImage());

                    break;
                case ITEM_VIEW_TYPE_SLEEP:
                    convertView = inflater.inflate(R.layout.healthinfo_sleep,parent, false);

                    TextView sleepTextView = (TextView)convertView.findViewById(R.id.sleepTime_id);
                    TextView goBedTextView = (TextView)convertView.findViewById(R.id.goBedTime_id);
                    TextView getUpTextView = (TextView)convertView.findViewById(R.id.getUpTime_id);

                    sleepTextView.setText(listViewItem.getSleepTime());
                    goBedTextView.setText(listViewItem.getGoBedTime());
                    getUpTextView.setText(listViewItem.getGetUpTime());
                    break;

                case ITEM_VIEW_TYPE_BEAT:
                    convertView = inflater.inflate(R.layout.healthinfo_heartbeat,parent, false);

                    TextView heartBeatTextView = (TextView)convertView.findViewById(R.id.HeartBeat_id);

                    heartBeatTextView.setText(listViewItem.getHeartBeat());
                    break;
            }
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(){
        ListViewItem item = new ListViewItem() ;
        item.setType(ITEM_VIEW_TYPE_MEDICINE) ;
        listViewItemList.add(item);
    }
    public void addItem(Drawable morningImage, Drawable afternoonImage,Drawable nightImage) {
        ListViewItem item = new ListViewItem() ;
        item.setType(ITEM_VIEW_TYPE_MEAL) ;
        item.setMorningImage(morningImage);
        item.setAfternoonImage(afternoonImage);
        item.setNightImage(nightImage);

        listViewItemList.add(item) ;
    }

    public void addItem(String sleepTime,String goBedTime,String getUpTime) {
        ListViewItem item = new ListViewItem() ;
        item.setType(ITEM_VIEW_TYPE_SLEEP) ;
        item.setSleepTime(sleepTime);
        item.setGoBedTime(goBedTime);
        item.setGetUpTime(getUpTime);

        listViewItemList.add(item);
    }

    public void addItem(String heartBeat) {
        ListViewItem item = new ListViewItem() ;
        item.setType(ITEM_VIEW_TYPE_BEAT) ;
        item.setHeartBeat(heartBeat);

        listViewItemList.add(item);
    }
}
