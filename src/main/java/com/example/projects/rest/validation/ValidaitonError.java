package com.example.projects.rest.validation;

public class ValidaitonError {

    private String path;
    private String message;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessgage(String message) {
        this.message = message;
    }
}
