package com.example.yelpapp;

import java.text.DecimalFormat;

public class Data {

//    private String textview1;
//    private String imageview;
    private String id;
    private String i;
    private String imageview;
    private String textview1;
    private String textview2;
    private String textview3;
    private String lat;
    private String lng;

    Data (String id, String i, String imageview, String textview1, String textview2, String textview3, String lat, String lng)
    {
        this.id = id;
        this.i = i;
        this.imageview = imageview;
        this.textview1=textview1;
        this.textview2=textview2;
        this.textview3=textview3;
        this.lat = lat;
        this.lng = lng;
    }

    public String getIdview() {
        return id;
    }

    public String getTextview0() {return i;}

    public String getImageview() {
        return imageview;
    }

    public String getTextview1() {
        return textview1;
    }

    public String getTextview2() {
        DecimalFormat df = new DecimalFormat("###.#");
        return String.valueOf(df.format(Double.parseDouble(textview2)/1609.34));
    }

    public String getTextview3() {
        return textview3;
    }

    public String getlat() {
        return lat;
    }

    public String getlng() {
        return lng;
    }

}
