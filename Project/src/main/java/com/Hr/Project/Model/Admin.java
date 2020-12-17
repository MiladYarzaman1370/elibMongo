package com.Hr.Project.Model;

import org.bson.types.ObjectId;

public class Admin extends User{
    public Admin(ObjectId id, String name, String email, String password, String mobileNumber, String type) {
        super(id, name, email, password, mobileNumber, type);
        this.type="admin";
    }
    //////////////////////////////////
    public void add(Book book)
    {
        System.out.println("admin->add() isCalled.");
    }
}