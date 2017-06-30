package com.styleapp.styleappadm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.classes.Instanced_Service;
import com.styleapp.styleappadm.classes.DetailServiceAdapter;
import com.styleapp.styleappadm.model.DetailService;

import java.util.ArrayList;

import static com.styleapp.styleappadm.VariablesGlobales.TAG;

public class Services_fragment extends Fragment{

    public Services_fragment() {
        // Required empty public constructor
    }
    private static final String DESCRIBABLE_KEY = "describable_key";
    private ArrayList<DetailService> detailServices;

    public static Services_fragment newInstance(ArrayList<DetailService> detailServices) {
        Services_fragment fragment = new Services_fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, detailServices);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.one_fragment, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);
        detailServices = (ArrayList<DetailService>) getArguments().getSerializable(DESCRIBABLE_KEY);
        for(int i=0; i<detailServices.size(); i++){
            Log.i(TAG,"servicio: " + detailServices.get(i).getService().getName());
        }
        DetailServiceAdapter adapter1=new DetailServiceAdapter(getActivity(), R.layout.instanced_service_list);
        adapter1.addAll(detailServices);
        rootView.setAdapter(adapter1);
        return view;
    }

}
