package com.airbnb.clone.backend.user.domain.event;


public enum UserEventType {

    USER_CREATED("user.created");

    private final String topic;

    UserEventType(String topic){
        this.topic = topic;
    }

    public String topic(){
        return this.topic;
    }
}
