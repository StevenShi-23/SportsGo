package com.example.sportsgo.sportsgo.utilities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.*;
import com.example.sportsgo.sportsgo.R;
import com.example.sportsgo.sportsgo.model.Facility;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import java.util.ArrayList;

import static com.example.sportsgo.sportsgo.MyApp.getContext;

/**
 * Created by apple on 6/4/17.
 */

public class ListAdapter extends ArrayAdapter<Facility> {
    private final ViewBinderHelper binderHelper;
    public ListAdapter(Context context, ArrayList<Facility> facilities) {
        super(context, 0, facilities);
        binderHelper = new ViewBinderHelper();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Facility facility = getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.brief_view_singleitem, parent, false);
        }
        // Lookup view for data population

        TextView fName = (TextView) convertView.findViewById(R.id.single_brief_name);
        TextView fTemp = (TextView) convertView.findViewById(R.id.single_brief_temperature);
        TextView fPsi = (TextView) convertView.findViewById(R.id.single_brief_psi);
        ImageView wIcon = (ImageView) convertView.findViewById(R.id.single_brief_weather_icon);
        if(facility.weather_status.toLowerCase().contains("cloudy".toLowerCase())){
            wIcon.setImageResource(R.drawable.cloud_icon);
        }
        else if(facility.weather_status.toLowerCase().contains("rain".toLowerCase())){
            wIcon.setImageResource(R.drawable.rain_icon);
        }
        else{
            wIcon.setImageResource(R.drawable.sunny_icon);
        }
        // Populate the data into the template view using the data object
        fName.setText(facility.facilityName);
        fTemp.setText(String.format( "%.1f", facility.temperature )+ (char) 0x00B0 + 'C');
        String psi_level;
        if(facility.psi<50) {
            psi_level = "Good";
            fPsi.setTextColor(Color.parseColor("#1a7734"));
        }
        else if(facility.psi<100){
            psi_level = "Moderate";
            fPsi.setTextColor(Color.parseColor("#ea860b"));}
        else{
            psi_level = "Unhealthy";
            fPsi.setTextColor(Color.parseColor("#e01c0b"));}
        fPsi.setText(String.format("PSI: %d  ",facility.psi) + psi_level);

        // Return the completed view to render on screen
        return convertView;
    }


    private class ViewHolder {
        TextView textView;
        View deleteView;
        SwipeRevealLayout swipeLayout;
    }




}
