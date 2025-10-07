package com.smartride.model;

abstract public class User {
    protected String userID;
    protected String userName;
    protected String phoneNumber;
    protected String email;
    protected Location currentLocation;

    public User(String userID, String userName, String phoneNumber, String email) {
        this.userID = userID;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public abstract String getUserType();

    @Override
    public String toString() {
        return String.format("'%s' {userID='%s', userName='%s', phoneNumber='%s'}", getUserType(), userID, userName, phoneNumber);
    }
}
