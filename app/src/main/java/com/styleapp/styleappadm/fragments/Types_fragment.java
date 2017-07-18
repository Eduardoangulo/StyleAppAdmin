package com.styleapp.styleappadm.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.classes.Types_adapter;

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
        return view;
    }

}