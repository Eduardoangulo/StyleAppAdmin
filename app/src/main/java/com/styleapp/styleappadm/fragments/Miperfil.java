package com.styleapp.styleappadm.fragments;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.styleapp.styleappadm.LoginActivity;
import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.connection_service.aviability.AvailabilityPost;
import com.styleapp.styleappadm.connection_service.aviability.AvailabilityResponse;
import com.styleapp.styleappadm.connection_service.styleapp_API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.styleapp.styleappadm.VariablesGlobales.conexion;
import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;

/**
 * Created by Luis on 17/07/2017.
 */

public class Miperfil extends Fragment {

    private ProgressDialog progress;

    public Miperfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.miperfil, container, false);
        if (currentWorker == null) {
            goLoginScreen();
        } else {
            progress = new ProgressDialog(getActivity());
            progress.setMessage(getResources().getString(R.string.loading));
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            displayProfileInfo(view);
            Switch aSwitch= (Switch) view.findViewById(R.id.switch_vis);
            aSwitch.setChecked(currentWorker.getUser().getStatus()==1);
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        currentWorker.getUser().setStatus(1);
                        changeStatus();

                    } else {
                        currentWorker.getUser().setStatus(0);
                        changeStatus();
                    }
                }
            });
        }
        return view;
    }
    private void changeStatus(){
        progress.show();
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
            Call<AvailabilityResponse> call= service.cambiarEstadoGeneral(new AvailabilityPost(currentWorker.getUserId(),currentWorker.getUser().getStatus()));
            call.enqueue(new Callback<AvailabilityResponse>() {
                @Override
                public void onResponse(Call<AvailabilityResponse> call, Response<AvailabilityResponse> response) {
                    progress.dismiss();
                    if(response.body().getSuccess())
                        Toast.makeText(getContext(),"Se cambio la disponibilidad", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"No se pudo cambiar la disponibilidad", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<AvailabilityResponse> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getContext(),"Error de conexion", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            progress.dismiss();
            Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }
    }
    private void displayProfileInfo(View view) {
        progress.show();
        TextView nameTextView= (TextView) view.findViewById(R.id.profileName);
        TextView telfTextView= (TextView) view.findViewById(R.id.profilePhone);
        TextView emailTextView= (TextView) view.findViewById(R.id.profileEmail);
        ImageView profileImg = (ImageView) view.findViewById(R.id.ProfilePicture);
        nameTextView.setText(currentWorker.getUser().getFirstName()+" "+currentWorker.getUser().getLastName());
        emailTextView.setText(currentWorker.getUser().getEmail());
        telfTextView.setText(currentWorker.getUser().getTelephone().toString());
        Glide.with(getContext())
                .load(currentWorker.getPhoto())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileImg);
        progress.dismiss();

    }
    private void goLoginScreen() {
        Intent intent= new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}