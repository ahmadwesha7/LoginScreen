package com.example.joserv_1.loginscreen;

/**
 * Created by weshah on 3/30/17.
 */

public class RegisterAdapter {

    private String First_Name;
    private String Last_Name;
    private String Eamil;
    private String Team_Viewer_Id;
    private String phone;
    private String Type;

    public RegisterAdapter(){

    }

    public RegisterAdapter(String first_Name, String last_Name, String eamil, String team_Viewer_Id, String phone, String type) {
        this.First_Name = first_Name;
        this.Last_Name = last_Name;
        this.Eamil = eamil;
        this.Team_Viewer_Id = team_Viewer_Id;
        this.phone = phone;
        this.Type = type;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.Last_Name = last_Name;
    }

    public String getEamil() {
        return Eamil;
    }

    public void setEamil(String eamil) {
        this.Eamil = eamil;
    }

    public String getTeam_Viewer_Id() {
        return Team_Viewer_Id;
    }

    public void setTeam_Viewer_Id(String team_Viewer_Id) {
        this.Team_Viewer_Id = team_Viewer_Id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }



}
