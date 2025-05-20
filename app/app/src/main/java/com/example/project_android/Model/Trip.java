package com.example.project_android.Model;

public class Trip {
    private String date;
    private String id;
    private String begin;
    private String end;
    private int income;

    public Trip(String date, String id, String begin, String end, int income) {
        this.date = date;
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.income = income;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
