package com.vereskul.movies.model;

public class UserRating {
    private String value;
    private String type;

    public UserRating() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
