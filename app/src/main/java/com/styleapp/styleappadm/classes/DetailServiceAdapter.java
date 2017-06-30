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
import com.styleapp.styleappadm.model.DetailService;


 /* Created by Luis on 05/05/2017.
 */

public class DetailServiceAdapter extends ArrayAdapter<DetailService> {
    private int r;
    public DetailServiceAdapter(Activity context, int resource){
        super(context, resource);
        r=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        DetailService currentDetail = getItem(position);

        TextView servicet = (TextView) listItemView.findViewById(R.id.serviceName);
        TextView service_status = (TextView)listItemView.findViewById(R.id.status);
        //ImageView img = (ImageView) listItemView.findViewById(R.id.basicImg);


        servicet.setText(currentDetail.getService().getName());
        service_status.setText(currentDetail.getStatus().toString());
        //img.setImageResource(currentDetail.getImgsrc());

        return listItemView;

    }

}
