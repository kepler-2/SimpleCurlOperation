package com.smad.bsnassignment.support;

import android.content.Context;
import android.provider.Settings;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExecuteTask {
    public static final String NAME ="name";
    public static final String MESSAGE ="message";
    public static final String STATUS ="status";
    public static final String DATA ="data";
    public static final String EMAIL ="email";
    public static final String USERNAME ="username";
    public static final String PASSWORD ="password";
    public static final String USER_TYPE ="user_type";
    public static final String USER_ID ="user_id";
    public static final String FEEDBACK ="feedback";
    public static final String BASE ="http://www.databricktechnologies.com/www/rohit/controller.php?action=";


    public static void getJsonObjectRequest(Context context, String url, Response.Listener<JSONObject> jsonObjectListener,Response.ErrorListener errorListener) {
        try {
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, jsonObjectListener,errorListener);


// Access the RequestQueue through your singleton class.
            Volley.newRequestQueue(context).add(jsonObjectRequest);
        } catch (Exception e) {

        }
    }

    public static void postJsonObjectRequest(Context context, String url, JSONObject jsonRequest, Response.Listener<JSONObject> jsonObjectListener, Response.ErrorListener errorListener) {
        try {
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonRequest, jsonObjectListener,errorListener);


// Access the RequestQueue through your singleton class.
            Volley.newRequestQueue(context).add(jsonObjectRequest);
        } catch (Exception e) {

        }
    }
}