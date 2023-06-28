package com.example.avangard;

import android.os.Build;

import java.time.LocalTime;
import java.util.Date;

public class Message {
    private String senderEmail;
    private String getterEmail;
    private String message;
    private long localTime;

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getGetterEmail() {
        return getterEmail;
    }

    public void setGetterEmail(String getterEmail) {
        this.getterEmail = getterEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLocalTime() {
        return localTime;
    }

    public void setLocalTime(long localTime) {
        this.localTime = localTime;
    }
    public Message(){
        localTime = new Date().getTime();

    }

    public Message(String senderEmail, String getterEmail, String message) {
        this.senderEmail = senderEmail;
        this.getterEmail = getterEmail;
        this.message = message;
        localTime = new Date().getTime();
    }
}
