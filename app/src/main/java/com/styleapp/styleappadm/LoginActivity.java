package com.styleapp.styleappadm;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.styleapp.styleappadm.connection_service.API_Connection;
import com.styleapp.styleappadm.connection_service.loginPost;
import com.styleapp.styleappadm.connection_service.loginResult;
import com.styleapp.styleappadm.connection_service.styleapp_API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.styleapp.styleappadm.VariablesGlobales.TAG;
import static com.styleapp.styleappadm.VariablesGlobales.URL_desarrollo;
import static com.styleapp.styleappadm.VariablesGlobales.conexion;
import static com.styleapp.styleappadm.VariablesGlobales.currentWorker;
import static com.styleapp.styleappadm.VariablesGlobales.loginPreferences;
import static com.styleapp.styleappadm.VariablesGlobales.loginPrefsEditor;

public class LoginActivity extends AppCompatActivity {

   // private TextView registerBTN;
    private String username,password;
    private TextView regularLogin;
    private EditText login_user;
    private EditText login_password;

    private CheckBox saveLoginCheckBox;
    private Boolean saveLogin;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "Entro LoginActivity");
        regularLogin= (TextView) findViewById(R.id.ingresarLogin);
        login_user= (EditText) findViewById(R.id.loginUser);
        login_password= (EditText) findViewById(R.id.loginPass);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            progress.show();
            login_user.setText(loginPreferences.getString("username", ""));
            login_password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            loginApi(login_user.getText().toString(), login_password.getText().toString());
        }

        regularLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(login_user.getWindowToken(), 0);

                username = login_user.getText().toString();
                password = login_password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                loginApi(login_user.getText().toString(), login_password.getText().toString());
            }
        });
    }

    private void loginApi(final String email, final String pass){
        progress.show();
        loginPost lPost = new loginPost(email, pass, FirebaseInstanceId.getInstance().getToken());
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
                            Toast.makeText(getApplicationContext(), "Bienvenido a Styleapp", Toast.LENGTH_SHORT).show();
                            currentWorker =response.body().getClient();
                            currentWorker.setLogedUsername(email);
                            currentWorker.setLogedPassword(pass);
                            goMainScreen();
                        }
                        else {
                            Log.i(TAG, "Usuario Incorrecto");
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Log.e(TAG, " Verificar onResponse: " + response.errorBody());
                    }
                    progress.dismiss();
                }
                @Override
                public void onFailure(Call<loginResult> call, Throwable t) {
                    Log.e(TAG, " Verificar onFailure: " + t.getMessage());
                    progress.dismiss();
                }
            });
        }else {
            Log.e(TAG, "Principal: se fue el internet");
            progress.dismiss();
        }
    }

    private void goMainScreen(){
        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
