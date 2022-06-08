package com.example.afinal.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CSVManager {

    Context context;

    public CSVManager(Context context) {
        this.context = context;
    }

    /**
     * Exports location list as a CSV file containing all data in a chart form
     */
    public void exportData(JSONArray jsonArray) {
        try {
            StringBuilder data = new StringBuilder();

            // Adding File Header
            data.append("FirstName, LastName, UserID, JobTitle, LastUpdate");

            // Adding Data
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                data.append("\n"
                        + obj.getString("first_name") + ","
                        + obj.getString("last_name") + ","
                        + obj.getString("user_id") + ","
                        + obj.getString("job_title") + ","
                        + obj.getString("last_update"));
            }

            // Saving file onto device
            FileOutputStream out = context.openFileOutput("terms_sign_export.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            // Exporting
            File fileLocation = new File(context.getFilesDir(), "terms_sign_export.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.afinal", fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".csv");
            fileIntent.setDataAndType(path, mimeType);
//            fileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(fileIntent);

            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_NEW_TASK);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            context.startActivity(fileIntent);
//            context.startActivity(Intent.createChooser(fileIntent, "Export CSV"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

}
