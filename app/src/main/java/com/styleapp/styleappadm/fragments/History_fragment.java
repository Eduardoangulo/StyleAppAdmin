package com.styleapp.styleappadm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.classes.DetailServiceAdapter;
import com.styleapp.styleappadm.connection_service.WorkerDetailPost;
import com.styleapp.styleappadm.connection_service.styleapp_API;
import com.styleapp.styleappadm.model.DetailService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleapp.styleappadm.VariablesGlobales.TAG;
import static com.styleapp.styleappadm.VariablesGlobales.conexion;
import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;


public class History_fragment extends Fragment{

    public History_fragment() {
        // Required empty public constructor
    }

    private ArrayList<DetailService> detailServices;
    private DetailServiceAdapter adapter1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.two_fragment, container, false);
        ListView rootView= (ListView) view.findViewById(R.id.list);

        adapter1=new DetailServiceAdapter(getActivity(), R.layout.instanced_service_list);
        rootView.setAdapter(adapter1);
        conexion.retrofitLoad();
        if (conexion.getRetrofit() != null) {
            requestData(conexion.getRetrofit());
        }

        return view;
    }
    private void requestData(Retrofit retrofit){
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<ArrayList<DetailService>> Call = service.getWorkerHistory(new WorkerDetailPost(currentWorker.getId()));
        Call.enqueue(new Callback<ArrayList<DetailService>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailService>> call, Response<ArrayList<DetailService>> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,"Se obtuvo historial_fragment del worker");
                    ArrayList<DetailService> historyDetails= new ArrayList<>();
                    detailServices=response.body();
                    for(int i=0; i<detailServices.size(); i++){
                        if(detailServices.get(i).getStatus()!=2){
                            historyDetails.add(detailServices.get(i));
                        }
                    }
                    adapter1.addAll(historyDetails);
                }
                else{
                    Log.e(TAG, "historial_fragment onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DetailService>> call, Throwable t) {
                Log.e(TAG, "historial_fragment onFailture: "+ t.getMessage());
            }
        });
    }

}
