package com.styleapp.styleappadm.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleapp.styleappadm.R;

/**
 * Created by Luis on 28/06/2017.
 */

public class achievements_adapter extends ArrayAdapter<Instanced_Service> {
    private int r;
    public achievements_adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        Instanced_Service currentService = getItem(position);

        TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
        ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);
        ImageView stars = (ImageView) listItemView.findViewById(R.id.logros_stars);

        servicet.setText(currentService.getServiceName());
        img.setImageResource(currentService.getImgsrc());
        stars.setImageResource(R.drawable.stars);

        return listItemView;

    }

}
