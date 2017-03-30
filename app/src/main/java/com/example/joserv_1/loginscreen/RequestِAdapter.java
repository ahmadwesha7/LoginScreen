package com.example.joserv_1.loginscreen;

/**
 * Created by weshah on 3/26/17.
 */

public class RequestِAdapter {

    private String Desc;
    private String Images;
    private String Location;
    private String Title;
    private String Video;
    private String Audio;


    public RequestِAdapter(){

    }

    public RequestِAdapter(String desc, String images, String location, String title, String video, String audio) {
        this.Desc = desc;
        this.Images = images;
        this.Location = location;
        this.Title = title;
        this.Video = video;
        this.Audio = audio;
    }



    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        this.Desc = desc;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        this.Images = images;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        this.Video = video;
    }

    public String getAudio() {
        return Audio;
    }

    public void setAudio(String audio) {
        this.Audio = audio;
    }


}
