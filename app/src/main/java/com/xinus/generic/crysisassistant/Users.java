package com.xinus.generic.crysisassistant;

class Users {

    String user_id,name, email, phonenumber;

    public Users(String user_id, String name, String email, String phonenumber) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public Users() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
