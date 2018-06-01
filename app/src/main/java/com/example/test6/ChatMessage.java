package com.example.test6;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String aSpeak;
    private int Speaker;
    private  String date;

    public ChatMessage() {
    }

    public ChatMessage(String aSpeak,int Speaker,Date date) {
        this.aSpeak = aSpeak;
        this.Speaker = Speaker;
        this.date = dateToString(date);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return df.format(date);
    }

    public String getaSpeak() {
        return aSpeak;
    }

    public int getSpeaker() {
        return Speaker;
    }

    public String getData(){
        return date;
    }

    public void setaSpeak(String aSpeak) {
        this.aSpeak = aSpeak;
    }

    public void setSpeaker(int Speaker) {
        this.Speaker = Speaker;
    }

    public void setDate(Date date){
        this.date = dateToString(date);
    }
}