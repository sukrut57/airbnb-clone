package com.airbnb.clone.backend.user.domain.model;

public class Authority {

    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Authority{" +
                ", name='" + name + '\'' +
                '}';
    }

}
