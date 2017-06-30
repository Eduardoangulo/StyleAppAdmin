package com.styleapp.styleappadm.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.model.Service;

/**
 * Created by Luis on 28/06/2017.
 */

public class achievements_adapter extends ArrayAdapter<Service> {
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
        Service currentService = getItem(position);

        TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
        ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);
        ImageView stars = (ImageView) listItemView.findViewById(R.id.logros_stars);

        servicet.setText(currentService.getName());

        switch(currentService.getId()){
            case 1:img.setImageResource(R.drawable.corte_hippie); break;
            case 2:img.setImageResource(R.drawable.corte_militar); break;
            case 3:img.setImageResource(R.drawable.corte_escolar); break;
            case 4:img.setImageResource(R.drawable.pedicure_dama); break;
            case 5:img.setImageResource(R.drawable.pedicure_caballero); break;
            case 6:img.setImageResource(R.drawable.manicure_dama); break;
            case 7:img.setImageResource(R.drawable.manicure_caballero); break;
            default:img.setImageResource(R.drawable.generictype); break;
        }
        switch(currentService.getValue_calc()){
            case 1:stars.setImageResource(R.drawable.stars_1); break;
            case 2:stars.setImageResource(R.drawable.stars_2); break;
            case 3:stars.setImageResource(R.drawable.stars_3); break;
            case 4:stars.setImageResource(R.drawable.stars_4); break;
            case 5:stars.setImageResource(R.drawable.stars_5); break;
            default: break;
        }
        return listItemView;

    }

}
