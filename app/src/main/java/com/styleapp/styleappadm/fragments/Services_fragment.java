package com.styleapp.styleappadm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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


public class Services_fragment extends Fragment{

    public Services_fragment() {
        // Required empty public constructor
    }

    private ArrayList<DetailService> detailServices=new ArrayList<>();
    private DetailServiceAdapter adapter1;
    private SwipeRefreshLayout refresh;
    private ArrayList<DetailService> historyDetails= new ArrayList<>();
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.one_fragment, container, false);

        progress = new ProgressDialog(getActivity());
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        ListView rootView= (ListView) view.findViewById(R.id.list);

        adapter1=new DetailServiceAdapter(getActivity(), R.layout.instanced_service_list);
        rootView.setAdapter(adapter1);

        iniciarLlamadaServicio();

        refresh= (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        return view;
    }
    private void requestData(Retrofit retrofit){
        if(adapter1!=null){
            adapter1.clear();
            detailServices.clear();
            historyDetails.clear();
        }
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<ArrayList<DetailService>> Call = service.getWorkerHistory(new WorkerDetailPost(currentWorker.getId()));
        Call.enqueue(new Callback<ArrayList<DetailService>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailService>> call, Response<ArrayList<DetailService>> response) {
                if(response.isSuccessful()){

                    detailServices=response.body();
                    for(int i=0; i<detailServices.size(); i++){
                        if(detailServices.get(i).getStatus()==2){
                            historyDetails.add(detailServices.get(i));
                        }
                    }
                    adapter1.addAll(historyDetails);
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<DetailService>> call, Throwable t) {
                Log.e(TAG, "historial_fragment onFailture: "+ t.getMessage());
                progress.dismiss();
            }
        });
    }
    private void refreshContent(){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iniciarLlamadaServicio();
                refresh.setRefreshing(false);
            }
        },0);
    }
    private void iniciarLlamadaServicio(){
        conexion.retrofitLoad();
        if (conexion.getRetrofit() != null) {
            progress.show();
            requestData(conexion.getRetrofit());
        }
    }

}
