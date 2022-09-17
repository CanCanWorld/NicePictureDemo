package com.zrq.nicepicturedemo.bean;

public class Pic {
    private int id;
    private String picUrl;
    private String thumbUrl;

    public Pic() {
    }

    public Pic(String picUrl, String thumbUrl) {
        this.picUrl = picUrl;
        this.thumbUrl = thumbUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                '}';
    }
}
