package com.styleapp.styleappadm.firebase;

import android.util.Log;

import static com.styleapp.styleappadm.VariablesGlobales.TAG;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Luis on 04/07/2017.
 */

public class InstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token= FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "token: "+token);
    }
}
