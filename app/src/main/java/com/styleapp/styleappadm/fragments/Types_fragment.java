package com.styleapp.styleappadm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.styleapp.styleappadm.R;

import static com.styleapp.styleappadm.VariablesGlobales.TAG;

/**
 * Created by Luis on 13/07/2017.
 */

public class Types_fragment extends Fragment {
    public Types_fragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.one_fragment, container, false);

        return view;
    }
}