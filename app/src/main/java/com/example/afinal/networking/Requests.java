package com.example.afinal.networking;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.afinal.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Requests {

    public static final String IP_ADDRESS = "192.168.56.1";

    public static void sendRegisterRequest(Context context,
                                           String firstName,
                                           String lastName,
                                           String cellPhone,
                                           String email,
                                           String uniqueId,
                                           String userId,
                                           int employeeType,
                                           OnServerResponse onServerResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + IP_ADDRESS + ":5000/api/v1.0/api/register";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response, onServerResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("email", email);
                params.put("user_id", userId);
                params.put("cell_phone", cellPhone);
                params.put("unique_id", uniqueId);
                params.put("is_employe", "0");
                params.put("type_employe", Integer.valueOf(employeeType).toString());
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    public static void sendGetTerms(Context context, OnServerResponse onServerResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + IP_ADDRESS + ":5000/api/v1.0/api/show_terms";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response, onServerResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("unique_id", User.getInstance().uniqueId);
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    public static void sendTermsRequest(Context context, String uniqueId, String jobTitle, OnServerResponse onServerResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + IP_ADDRESS + ":5000/api/v1.0/api/terms_of_use";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response, onServerResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("job_title", jobTitle);
                params.put("unique_id", uniqueId);
                params.put("authorized_signatory", "1");
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    public static void sendLoginRequest(Context context, String cellPhone, String workNumber, OnServerResponse onServerResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + IP_ADDRESS + ":5000/api/v1.0/api/login_user";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response, onServerResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("cell_phone", cellPhone);
                params.put("unique_id", workNumber);
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    private static void parseResponse(String response, OnServerResponse onServerResponse) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("status")) {
                String status = jsonObject.getString("status");
                if (status.equals("1"))
                    onServerResponse.onSuccess(jsonObject);
                else if (status.equals("0"))
                    onServerResponse.onFailure(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnServerResponse {
        public void onSuccess(JSONObject response);
        public void onFailure(String errorMessage);
    }
}
