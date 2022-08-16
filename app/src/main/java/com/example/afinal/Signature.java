package com.example.afinal;

import org.json.JSONObject;

public class Signature {
    public String name;
    public String jobTitle;
    public String id;
    public String date;

    public Signature(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("first_name") +" "+ jsonObject.getString("last_name");
            this.jobTitle = jsonObject.getString("job_title");
            this.id = jsonObject.getString("user_id");
            this.date = jsonObject.getString("last_update");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
