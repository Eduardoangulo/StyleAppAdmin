package com.styleapp.styleappadm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.classes.achievements_adapter;
import com.styleapp.styleappadm.model.Service;

import java.util.Collection;

import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;


public class Achievements_fragment extends Fragment{

    public Achievements_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.three_fragment, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);

        achievements_adapter adapter1=new achievements_adapter(getActivity(), R.layout.logros_list);
        adapter1.addAll(currentWorker.getTypes().get(0).getServices());

        rootView.setAdapter(adapter1);
        return view;
    }

}
