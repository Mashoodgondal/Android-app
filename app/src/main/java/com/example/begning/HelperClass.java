package com.example.begning;

public class HelperClass {
    String name, userEmail, userPassword;

    public HelperClass(String name, String email, String password) {
        this.name = name;
        this.userEmail = email;
        this. userPassword = password;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getPassword() {
        return  userPassword;
    }

    public void setPassword(String password) {
        this. userPassword = password;
    }



    public String getName() {
        return name;
    }

   
}
