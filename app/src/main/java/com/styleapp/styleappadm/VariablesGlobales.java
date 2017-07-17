package com.styleapp.styleappadm;


import android.content.SharedPreferences;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.Marker;
import com.styleapp.styleappadm.connection_service.API_Connection;
import com.styleapp.styleappadm.connection_service.position.PositionPost;
import com.styleapp.styleappadm.model.DetailService;
import com.styleapp.styleappadm.model.Worker;

/**
 * Created by eduardo on 6/11/17.
 */

public class VariablesGlobales {
    public static PositionPost positionPost=new PositionPost(-1, -1.0, -1.0);
    public static String URL_desarrollo = "http://styleapphome.prodequa.com/api/";
    public final static String TAG="STYLEAPPLOGS";
    public static API_Connection conexion;
    public static Worker currentWorker;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static DetailService currentDetail;
    public static SharedPreferences loginPreferences;
    public static SharedPreferences.Editor loginPrefsEditor;
    public final static String keyNoti="keyNoti";
}
