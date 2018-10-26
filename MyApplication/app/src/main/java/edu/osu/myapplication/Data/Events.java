package edu.osu.myapplication.Data;

import java.util.Date;

public class Events {

    private String event_id;
    private String eventName;
    private String location;
    private Date eventDate;
    private String categoryID;

    public Events(){
    }
    public Events(String eventName, String categoryID, String event_id){
        this.eventName = eventName;
        this.categoryID = categoryID;
        this.event_id = event_id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

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
}
