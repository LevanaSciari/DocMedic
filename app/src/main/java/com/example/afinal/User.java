package com.example.afinal;

import org.json.JSONArray;
import org.json.JSONObject;

public class User {
    public static final int EMPLOYEE_TYPE_INTERNAL = 0;
    public static final int EMPLOYEE_TYPE_EXTERNAL = 1;
    public static final int EMPLOYEE_TYPE_STUDENT = 2;

    private static User user;
    public String firstName;
    public String lastName;
    public String uniqueId;
    public String cellPhone;
    public String email;
    public String userID;
    public boolean isLoggedIn;
    public boolean isManager;
    public boolean didAgreeTerms;
    public int employeeType;

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void parseUser(JSONObject response) {
        try {
            JSONArray dataArr = response.getJSONArray("data");
            JSONObject dataObject = dataArr.getJSONObject(0);
            this.firstName = dataObject.getString("first_name");
            this.lastName = dataObject.getString("last_name");
            this.email = dataObject.getString("email");
            this.userID = dataObject.getString("user_id");
            this.cellPhone = dataObject.getString("cell_phone");
            this.uniqueId = dataObject.getString("unique_id");
            this.employeeType = dataObject.getInt("type_employe");
            this.isManager = dataObject.getInt("is_manager") == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User() {}
}
