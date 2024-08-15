package com.java3.bean;

/**
 * ORM编程思想：Object Relational Mapping
 * - 一个数据表对应一个Java类
 * - 表中的一条记录对应Java类中的一个对象
 * - 表中的一个字段对应Java类的一个属性
 */

import java.util.Date;

public class Customers {
    private String name;
    private String email;
    private Date birth;

    public Customers() {
        super();
    }

    public Customers(String name, String email, Date birth) {
        this.name = name;
        this.email = email;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}
