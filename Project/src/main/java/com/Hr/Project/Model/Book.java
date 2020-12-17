package com.Hr.Project.Model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Book {
    @BsonProperty("_id")
    @BsonId
    private ObjectId id;
    private String name;
    private String auther;
    private String publisher;
    private int quantity;

    public Book() {
    }

    public Book(ObjectId id, String name, String auther, String publisher, int quantity) {
        this.id = id;
        this.name = name;
        this.auther = auther;
        this.publisher = publisher;
        this.quantity = quantity;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    ////////////////////////////////////////////////////////
}
