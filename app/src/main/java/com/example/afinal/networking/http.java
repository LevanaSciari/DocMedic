package com.example.afinal.networking;

import android.os.AsyncTask;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.entity.StringEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicHeader;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.params.BasicHttpParams;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.params.HttpParams;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;


import java.io.IOException;
public class http extends AsyncTask<Void, Void, Void> {
    public static String http_post(String url, String data) {
        HttpParams myParams = new BasicHttpParams();
        HttpClient httpclient = new DefaultHttpClient(myParams);
        try {
            String prefix_url = "http://localhost:5000";
            HttpPost httppost = new HttpPost(prefix_url + url);
            httppost.setHeader("Content-type", "application/json");
            StringEntity se = new StringEntity(data);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(se);
            System.out.println(httppost+" "+data);
            HttpResponse response = httpclient.execute(httppost);
            System.out.println(response);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("test"+result);
            return result;
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}