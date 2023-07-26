package com.example.yelpapp;

public class reviewData {

    private String name;
    private String rating;
    private String text;
    private String date;

    reviewData (String name, String rating, String text, String date)
    {
        this.name = name;
        this.rating = rating;
        this.text = text;
        this.date=date;
    }

    public String getnameview() {
        return name;
    }

    public String getratingview() {return rating;}

    public String gettextview() {
        return text;
    }

    public String getdateview() {
        return date;
    }

}
