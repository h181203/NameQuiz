package com.example.randimarie.namequiz;

public class Person {

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
