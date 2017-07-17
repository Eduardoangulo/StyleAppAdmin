package com.styleapp.styleappadm.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.model.Type;

/**
 * Created by Luis on 16/07/2017.
 */

public class Types_adapter extends ArrayAdapter<Type> {
    private int r;
    public Types_adapter(Activity context, int resource){
        super(context, resource);
        r=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(r, parent, false);
        }
        Type currentType = getItem(position);

        TextView typename = (TextView) listItemView.findViewById(R.id.typeText);
        final TextView disp = (TextView)listItemView.findViewById(R.id.dispText);
        ImageView img = (ImageView) listItemView.findViewById(R.id.typeImg);
        Switch dispSwitch= (Switch) listItemView.findViewById(R.id.switch_disp);

        typename.setText(currentType.getName());
        if(dispSwitch.isChecked()){
            disp.setText("Disponible");
        }
        else{
            disp.setText("No disponible");
        }

        dispSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    disp.setText("Disponible");

                } else {
                    disp.setText("No disponible");
                }
            }
        });
        switch (currentType.getId()){
            case 1: img.setImageResource(R.drawable.haircut_icon); break;
            case 2: img.setImageResource(R.drawable.pedicure); break;
            case 3: img.setImageResource(R.drawable.manicure); break;
            default: img.setImageResource(R.drawable.generictype); break;
        }


        return listItemView;

    }


}