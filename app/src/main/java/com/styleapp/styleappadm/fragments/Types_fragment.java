package com.styleapp.styleappadm.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.styleapp.styleappadm.MainActivity;
import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.classes.Types_adapter;

import static com.styleapp.styleappadm.MainActivity.requestSingleUpdate;
import static com.styleapp.styleappadm.VariablesGlobales.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.styleapp.styleappadm.VariablesGlobales.TAG;
import static com.styleapp.styleappadm.VariablesGlobales.conexion;
import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;

/**
 * Created by Luis on 13/07/2017.
 */

public class Types_fragment extends Fragment {
    public Types_fragment(){}
    private Types_adapter adapter1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.types_fragment, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);
        adapter1=new Types_adapter(getActivity(), R.layout.types_list);
        rootView.setAdapter(adapter1);
        adapter1.addAll(currentWorker.getTypes());

        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            if(checkLocationPermission()){
                requestSingleUpdate(getActivity());
            }
        }
        else{
            Log.e(TAG, "Principal: se fue el internet");
        }
        return view;
    }
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission. ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission. ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Styleapp necesita tu ubicación!")
                        .setMessage("Activar la ubicacion ayuda a encontrar a los estilistas mas cercanos a ti")
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }else{
            return true;
        }
    }
}