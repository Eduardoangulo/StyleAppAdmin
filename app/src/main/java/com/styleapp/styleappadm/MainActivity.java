package com.styleapp.styleappadm;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.styleapp.styleappadm.connection_service.API_Connection;
import com.styleapp.styleappadm.connection_service.WorkerDetailPost;
import com.styleapp.styleappadm.connection_service.styleapp_API;
import com.styleapp.styleappadm.fragments.Services_fragment;
import com.styleapp.styleappadm.fragments.Achievements_fragment;
import com.styleapp.styleappadm.fragments.History_fragment;
import com.styleapp.styleappadm.model.DetailService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.styleapp.styleappadm.VariablesGlobales.URL_desarrollo;
import static com.styleapp.styleappadm.VariablesGlobales.conexion;
import static com.styleapp.styleappadm.VariablesGlobales.TAG;
import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<DetailService> detailServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Entro MainActivity");
        conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
        if(currentWorker ==null){
                Log.i(TAG, "currentWorker Null");
                finish();
                goLoginScreen();
        }
        else{
            Log.i(TAG, "currentWorker NO null");
            conexion.retrofitLoad();
            if(conexion.getRetrofit()!=null){
                requestData(conexion.getRetrofit());
            }
            else{
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }


    }

    private void requestData(Retrofit retrofit){
        styleapp_API service = retrofit.create(styleapp_API.class);
        Call<ArrayList<DetailService>> Call = service.getWorkerHistory(new WorkerDetailPost(currentWorker.getId()));
        Call.enqueue(new Callback<ArrayList<DetailService>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailService>> call, Response<ArrayList<DetailService>> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,"Se obtuvo historial del worker");
                    detailServices=response.body();
                    viewPager = (ViewPager) findViewById(R.id.viewpager);
                    setupViewPager(viewPager);

                    tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);

                }
                else{
                    Log.e(TAG, "MainAcitity onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DetailService>> call, Throwable t) {
                Log.e(TAG, "MainAcitity onFailture: "+ t.getMessage());
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ArrayList<DetailService> servicesDetails=new ArrayList<>();
        ArrayList<DetailService> historyDetails= new ArrayList<>();

        for(int i=0; i<detailServices.size(); i++){
           if(detailServices.get(i).getStatus()==2){
                servicesDetails.add(detailServices.get(i));
           }
           else{
               historyDetails.add(detailServices.get(i));
           }
        }

        Fragment svFragment =Services_fragment.newInstance(servicesDetails);
        Fragment historyFragmentnew= History_fragment.newInstance(historyDetails);

        adapter.addFragment(svFragment, getResources().getString(R.string.servicios));
        adapter.addFragment(historyFragmentnew, getResources().getString(R.string.historial));
        adapter.addFragment(new Achievements_fragment(), getResources().getString(R.string.logros));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void goLoginScreen() {
        Intent intent= new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
