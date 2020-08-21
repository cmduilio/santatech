package com.samit.entrypoints.dto;

import com.google.gson.annotations.SerializedName;

public class MeetupUserDto {

    @SerializedName("user_name")
    private String userName;
    @SerializedName("meetup_date")
    private String meetupDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMeetupDate() {
        return meetupDate;
    }

    public void setMeetupDate(String meetupDate) {
        this.meetupDate = meetupDate;
    }
}
