package com.example.project_android.Model;

import android.provider.ContactsContract;

import java.util.Date;

public class Complaint {
    private String id;
    private String restaurantName;
    private String typeComplaint;
    private String complaintContent;
    private Date date;
    private boolean isResolved;

    public Complaint(String id, String restaurantName, String typeComplaint, String complaintContent, Date date, boolean isResolved) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.typeComplaint = typeComplaint;
        this.complaintContent = complaintContent;
        this.date = date;
        this.isResolved = isResolved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTypeComplaint() {
        return typeComplaint;
    }

    public void setTypeComplaint(String typeComplaint) {
        this.typeComplaint = typeComplaint;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }
}
