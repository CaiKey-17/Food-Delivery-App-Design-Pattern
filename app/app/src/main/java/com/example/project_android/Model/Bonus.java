package com.example.project_android.Model;

import java.util.Date;

public class Bonus {
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public String getTitle() {
        return title;
    }

    public Bonus(Date time, Date until, String title, String description) {
        this.time = time;
        this.until = until;
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Date time;
    private Date until;
    private String title;
    private String description;
}
