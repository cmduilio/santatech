package com.samit.core.entities;

import java.util.Set;

public class Meetup {
    public static final String COLLECTION_NAME = "Meetup";

    private String date;
    private Set<String> attendees;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Set<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<String> attendees) {
        this.attendees = attendees;
    }
}
