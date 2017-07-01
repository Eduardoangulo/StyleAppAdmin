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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
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
                currentWorker=null;
                goLoginScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
