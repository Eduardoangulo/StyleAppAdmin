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
import android.widget.Toast;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.connection_service.availability.AvailabilityResponse;
import com.styleapp.styleappadm.connection_service.availability.ServiceAvailabilityPost;
import com.styleapp.styleappadm.connection_service.styleapp_API;
import com.styleapp.styleappadm.model.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.styleapp.styleappadm.VariablesGlobales.conexion;
import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;

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
        final Type currentType = getItem(position);

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

                    changeServiceStatus(currentType.getId(), 1, disp);

                } else {

                    changeServiceStatus(currentType.getId(), 0, disp);
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
    private void changeServiceStatus(int type_id, final int status, final TextView disp){
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
            Call<AvailabilityResponse> call= service.cambiarEstadoServicio(new ServiceAvailabilityPost(currentWorker.getUserId(), type_id, status));
            call.enqueue(new Callback<AvailabilityResponse>() {
                @Override
                public void onResponse(Call<AvailabilityResponse> call, Response<AvailabilityResponse> response) {
                    if(response.body().getSuccess()){
                        if(status==1){
                            disp.setText("Disponible");
                        }
                        else{
                            disp.setText("No disponible");
                        }
                        Toast.makeText(getContext(),"Se cambio la disponibilidad", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(),"No se pudo cambiar la disponibilidad", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AvailabilityResponse> call, Throwable t) {
                    Toast.makeText(getContext(),"Error de conexion", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
           // progress.dismiss();
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }
    };

}