package com.example.vivek.myapplication_chat;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vivek on 24/9/17.
 */
public class VolleyWrapper {

    public static void getJsonObjectFromServer(Context context, String url,
                                               int method, JSONObject jsonObject,final String TAG,
                                               final JSONObjectResponseHandler responseHandler) {
        // Tag used to cancel the request
        //String tag_json_obj = "json_obj _req";
        VolleyLog.d(TAG, "Url: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        responseHandler.onResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                responseHandler.onErrorResponse(error);
            }
        }) {

        };

        MySingelton.getInstance(context).addToRequestQueue(jsonObjectRequest, TAG);
    }
}
