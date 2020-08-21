package com.samit.core.entities;

import java.util.Set;

public class User {
    public static final String COLLECTION_NAME = "User";

    private String name;
    private boolean admin;
    private Set<String> meetups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<String> getMeetups() {
        return meetups;
    }

    public void setMeetups(Set<String> meetups) {
        this.meetups = meetups;
    }
}
