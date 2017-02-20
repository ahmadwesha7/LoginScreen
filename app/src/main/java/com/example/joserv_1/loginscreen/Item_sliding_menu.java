package com.example.joserv_1.loginscreen;

/**
 * Created by JoServ-1 on 15/02/2017.
 */

public class Item_sliding_menu {
    private  int ImgId;
    private String title;

    public Item_sliding_menu(int imgId, String title) {
        ImgId = imgId;
        this.title = title;
    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
