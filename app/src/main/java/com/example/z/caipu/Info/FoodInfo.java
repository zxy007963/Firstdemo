package com.example.z.caipu.Info;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class FoodInfo {
    String title;
    String img_url;
    int id;

    public FoodInfo(String title, String img_url, int id) {
        this.title = title;
        this.img_url = img_url;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
