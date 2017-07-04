package com.styleapp.styleappadm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.classes.DetailServiceAdapter;
import com.styleapp.styleappadm.connection_service.API_Connection;
import com.styleapp.styleappadm.connection_service.WorkerDetailPost;
import com.styleapp.styleappadm.connection_service.notifications.Datos;
import com.styleapp.styleappadm.connection_service.notifications.Notificacion;
import com.styleapp.styleappadm.connection_service.notifications.NotificationPost;
import com.styleapp.styleappadm.connection_service.notifications.NotificationResponse;
import com.styleapp.styleappadm.connection_service.notifications_API;
import com.styleapp.styleappadm.connection_service.status.StatusPost;
import com.styleapp.styleappadm.connection_service.status.StatusResponse;
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
    private API_Connection noti;
    private DetailService currentDetail;

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

        noti= new API_Connection(getContext(), TAG, "https://fcm.googleapis.com/");

        adapter1=new DetailServiceAdapter(getActivity(), R.layout.instanced_service_list);
        rootView.setAdapter(adapter1);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)  {
                if(historyDetails==null){
                    view.setClickable(false);
                }
                else{
                    currentDetail=historyDetails.get(position);
                    conexion.retrofitLoad();
                    if (conexion.getRetrofit() != null) {
                        ServiceDialog dialog= new ServiceDialog();
                        dialog.setmListener(new ServiceDialog.ServiceDialogListener() {

                            @Override
                            public void onDialogPositiveClick(DialogFragment dialog) {
                                progress.show();
                                confirmService(conexion.getRetrofit());
                            }

                            @Override
                            public void onDialogNegativeClick(DialogFragment dialog) {
                                progress.show();
                                cancelService(conexion.getRetrofit());
                            }
                        });
                        dialog.show(getFragmentManager() , "StyleApp");

                    }
                }
            }
        });

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

    private void cancelService(Retrofit retrofit) {
        StatusPost statuspost= new StatusPost(currentDetail.getId());
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<StatusResponse> call= service.cancelService(statuspost);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                progress.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Se cancelo el servicio", Toast.LENGTH_SHORT).show();
                    enviarNotificacion(currentWorker.getUser().getFirstName()+" rechazó el servicio");

                }
                else{
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void confirmService(Retrofit retrofit) {
        StatusPost statuspost= new StatusPost(currentDetail.getId());
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<StatusResponse> call= service.confirmarServicio(statuspost);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                progress.dismiss();
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Se aceptó el servicio", Toast.LENGTH_SHORT).show();
                    enviarNotificacion(currentWorker.getUser().getFirstName()+" aceptó el servicio");
                }
                else{
                    Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void enviarNotificacion(String msg){
        noti.retrofitLoad();
        if(noti.getRetrofit()!=null){
            Log.i(TAG, "Notificaciones: Hay internet");
            Log.i(TAG, "Enviar Notificacion");
            String token;
            if(currentDetail.getClient().get(0).getToken()==null){
                token="gg";
            }
            else{
                token=currentDetail.getClient().get(0).getToken();
            }
            NotificationPost nPost= new NotificationPost(token, new Notificacion("Informacion sobre el servicio que solicitaste", msg), new Datos("Enviado deade app","Enviado deade app"));
            notifications_API service= noti.getRetrofit().create(notifications_API.class);
            Call<NotificationResponse> Call= service.enviarNotificacion(nPost);
            Call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getSuccess()==1){
                            Log.i(TAG, "Se envio la notificacion");
                        }
                        else{
                            Log.i(TAG, "Error al enviar notificacion");
                        }
                    }
                    else{
                        Log.e(TAG, " Notificacion-onResponse: " + response.errorBody());
                    }
                }
                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    Log.e(TAG, " Notificacion-onFailure: " + t.getMessage());
                }
            });
        }else
        {
            Log.e(TAG, "Notificaciones: se fue el internet");
        }
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
                        if(detailServices.get(i).getStatus()>1){
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
