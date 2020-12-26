package com.subhechhu.bhadama.networking;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.subhechhu.bhadama.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class APIRequest {

    private static final String TAG = APIRequest.class.getSimpleName();
    final RequestQueue queue;
    final FromAPI fromAPI;

    public APIRequest(FromAPI fromAPI) {
        this.fromAPI = fromAPI;
        queue = Volley.newRequestQueue(AppController.getInstance());
    }

    public void makePostRequest(String url, Map<String, String> params) {
        Log.e(TAG, "post url: " + url);
        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                response -> {
                    Log.e(TAG, "post respnse: " + response.toString());
                    fromAPI.getResponse(response.toString());
                },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(req);
    }

    public void makeGetRequest(String url) {
        Log.e(TAG, "get url: " + url);
        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.e(TAG, "getResponse: " + response);
                    fromAPI.getResponse(response);
                },
                error -> VolleyLog.e(TAG, "Error: " + error.getMessage()));

        queue.add(req);
    }

    public interface FromAPI {
        void getResponse(String data);
    }
}
