package com.example.schedulifeproject;

import java.util.Objects;


public class Users {
    private String Username;
    private String Password;
    private String Email;


    public Users (String username, String password, String email) {
        this.Username=username;
        this.Password=password;
        this.Email=email;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Username.equals(users.Username) && Password.equals(users.Password) && Email.equals(users.Email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(Username, Password, Email);
    }
}
