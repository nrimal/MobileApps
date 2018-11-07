package edu.osu.myapplication.Data;

import java.util.Calendar;

public class Events {

    private String event_id;
    private String eventName;
    private String location;
    private Calendar eventDateTime;
    private String categoryID;

    public Events(String eventName, String categoryID, Calendar dateTime){
        this.eventName = eventName;
        this.categoryID = categoryID;
        this.eventDateTime = dateTime;
        this.event_id = dateTime.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEventDate(Calendar eventDate) { this.eventDateTime = eventDate; }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public Calendar getEventDate() { return eventDateTime; }
}
