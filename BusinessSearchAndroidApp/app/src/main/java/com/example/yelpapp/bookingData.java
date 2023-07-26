package com.example.yelpapp;

public class bookingData {

    private String bookingIndex;
    private String businessName;
    private String bookingDate;
    private String bookingTime;
    private String bookingMail;

    public bookingData(String bookingIndex, String businessName, String bookingDate, String bookingTime, String bookingMail) {
        this.bookingIndex = bookingIndex;
        this.businessName = businessName;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingMail = bookingMail;
    }

    public String getBookingIndex() {
        return bookingIndex;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getBookingMail() {
        return bookingMail;
    }

}
