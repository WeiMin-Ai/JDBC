package com.java3.bean;

import java.util.Date;

public class InputData {
    private String name;
    private String email;
    private Date birth;
    private String photo;

    public InputData() {
        super();
    }

    public InputData(String name, String email, Date birth, String photo) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "InputData{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", photo='" + photo + '\'' +
                '}';
    }
}
