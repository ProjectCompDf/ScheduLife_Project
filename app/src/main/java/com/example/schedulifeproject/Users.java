package com.example.schedulifeproject;

public class Users {
    private String Username;
    private String Password;
    private String Gmail;

    public Users (String username, String password, String gmail) {
        this.Username=username;
        this.Password=password;
        this.Gmail=gmail;
    }
    public Users() {

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }
}
