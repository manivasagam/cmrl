package com.example.administrator.metroalerts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 8/26/2015.
 */
public class FindRouteAdapter extends ArrayAdapter<TrainInfo> {
    private final Context context;
    private final List<TrainInfo> values;

    public FindRouteAdapter(Context context,List<TrainInfo> values) {
        super(context, R.layout.route_info_item,values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.route_info_item, parent, false);
        TextView train_name = (TextView) rowView.findViewById(R.id.train_name);
        TextView train_descp = (TextView) rowView.findViewById(R.id.train_descp);
        TextView fare_ = (TextView) rowView.findViewById(R.id.fare_);
        TextView approx_time_interval = (TextView) rowView.findViewById(R.id.approx_time_interval);
        TextView arrival_time = (TextView) rowView.findViewById(R.id.arrival_time);
        TextView approx_distance = (TextView) rowView.findViewById(R.id.approx_distance);
        //TextView to_station = (TextView) rowView.findViewById(R.id.to_station);


        train_name.setText(values.get(position).name);
        train_descp.setText(values.get(position).descp);
        fare_.setText(values.get(position).fare);
        approx_time_interval.setText(values.get(position).time_interval);
        arrival_time.setText(values.get(position).arrival_time);
        approx_distance.setText(values.get(position).distance);
        //to_station.setText(values.get(position).Stationname);

        return rowView;
    }
}