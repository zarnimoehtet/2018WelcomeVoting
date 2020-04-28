package com.xyz.zarni.voting;

/**
 * Created by Zarni on 12/22/2017.
 */

public class Users {

    private String year, state, kvote,qvote, permission,princevote,princessvote;

    public Users() {
    }

    public Users(String year,  String kvote, String qvote, String permission) {
        this.year = year;
        this.kvote = kvote;
        this.qvote = qvote;
        this.permission = permission;

    }

    public String getPrincevote() {
        return princevote;
    }

    public void setPrincevote(String princevote) {
        this.princevote = princevote;
    }

    public String getPrincessvote() {
        return princessvote;
    }

    public void setPrincessvote(String princessvote) {
        this.princessvote = princessvote;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKvote() {
        return kvote;
    }

    public void setKvote(String kvote) {
        this.kvote = kvote;
    }

    public String getQvote() {
        return qvote;
    }

    public void setQvote(String qvote) {
        this.qvote = qvote;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
