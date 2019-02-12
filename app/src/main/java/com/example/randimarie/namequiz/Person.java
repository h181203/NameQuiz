package com.example.randimarie.namequiz;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Person implements Serializable {

    @Exclude private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private String image;

    public Person(){};

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Person(String name, String image){
        this.name = name;
        this.image = image;

    }
}
