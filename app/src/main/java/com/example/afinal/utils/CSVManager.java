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
import java.text.DateFormat;
import java.util.Date;
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

            // Adding File Header, which will be the columns in the docs file
            data.append("FirstName, LastName, UserID, JobTitle, LastUpdate");

            // Adding Data to file
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);

                long t = Long.parseLong(obj.getString("last_update")) * 1000;
                long HOUR = 3600*1000; // in milli-seconds.
                Date date = new Date(t + 3 * HOUR);
//                DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date));

                data.append("\n"
                        + obj.getString("first_name") + ","+ obj.getString("last_name") + ","
                        + obj.getString("user_id") + ","
                        + obj.getString("job_title") + ","
                        + DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date));
            }

            // Saving file onto device
            FileOutputStream out = context.openFileOutput("terms_sign_export.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            // Exporting file from device storage to sharing flow
            File fileLocation = new File(context.getFilesDir(), "terms_sign_export.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.afinal", fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".csv");
            fileIntent.setDataAndType(path, mimeType);

            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_NEW_TASK);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            context.startActivity(fileIntent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
