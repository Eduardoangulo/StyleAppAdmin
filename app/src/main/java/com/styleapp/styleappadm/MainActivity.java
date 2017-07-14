package com.styleapp.styleappadm;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.styleapp.styleappadm.connection_service.API_Connection;
import com.styleapp.styleappadm.connection_service.WorkerDetailPost;
import com.styleapp.styleappadm.connection_service.loginPost;
import com.styleapp.styleappadm.connection_service.loginResult;
import com.styleapp.styleappadm.connection_service.styleapp_API;
import com.styleapp.styleappadm.fragments.Services_fragment;
import com.styleapp.styleappadm.fragments.Achievements_fragment;
import com.styleapp.styleappadm.fragments.History_fragment;
import com.styleapp.styleappadm.fragments.Types_fragment;
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
import static com.styleapp.styleappadm.VariablesGlobales.loginPrefsEditor;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Entro MainActivity");
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
        Log.i(TAG,"Token Firebase: "+ FirebaseInstanceId.getInstance().getToken());
        if(currentWorker ==null){
                Log.i(TAG, "currentWorker Null");
                finish();
                goLoginScreen();
        }
        else{
            Log.i(TAG, "currentWorker NO null");
            tabLayout.setupWithViewPager(viewPager);
            setupViewPager(viewPager);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Types_fragment(), getResources().getString(R.string.types));
        adapter.addFragment(new Services_fragment(), getResources().getString(R.string.servicios));
        adapter.addFragment(new History_fragment(), getResources().getString(R.string.historial));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cerrar_sesion:
                progress.show();
                //cleanStack();
                LogOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void cleanStack(){
        FragmentManager fm = this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    private void LogOut(){
        loginPrefsEditor.clear();
        loginPrefsEditor.commit();
        Log.i(TAG, "User: "+currentWorker.getLogedUsername()+" password: "+currentWorker.getLogedPassword());
        loginPost lPost = new loginPost(currentWorker.getLogedUsername(), currentWorker.getLogedPassword(), currentWorker.getLogedUsername());
        if(conexion==null){
            conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
        }
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
            Call<loginResult> Call = service.login(lPost);
            Call.enqueue(new Callback<loginResult>() {
                @Override
                public void onResponse(Call<loginResult> call, Response<loginResult> response) {
                    if (response.isSuccessful()) {

                        if(response.body().getSuccess()){
                            Log.i(TAG, "Usuario Correcto");
                            currentWorker=null;
                            goLoginScreen();
                            progress.dismiss();
                        }
                        else {
                            Log.i(TAG, "Datos dañados");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                        Log.e(TAG, " logOut onResponse: " + response.errorBody());
                        progress.dismiss();
                    }

                }
                @Override
                public void onFailure(Call<loginResult> call, Throwable t) {
                    Log.e(TAG, " logOut onFailure: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            });
        }else {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Principal: se fue el internet");
        }
    }
}
