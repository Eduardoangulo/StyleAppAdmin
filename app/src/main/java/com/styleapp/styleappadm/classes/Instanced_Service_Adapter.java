package com.styleapp.styleappadm.classes;

/**
 * Created by Luis on 28/06/2017.
 */

/**
 * Created by Luis on 06/06/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.styleapp.styleappadm.R;


 /* Created by Luis on 05/05/2017.
 */

public class Instanced_Service_Adapter extends ArrayAdapter<Instanced_Service> {
    private int r;
    public Instanced_Service_Adapter(Activity context, int resource){
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
        TextView service_status = (TextView)listItemView.findViewById(R.id.status);
        ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);


        servicet.setText(currentService.getServiceName());
        service_status.setText(currentService.getState());
        img.setImageResource(currentService.getImgsrc());

        return listItemView;

    }

}
