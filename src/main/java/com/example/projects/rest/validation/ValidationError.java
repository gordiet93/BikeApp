package com.example.projects.rest.validation;

public class ValidationError {

    private String path;
    private String message;

    public ValidationError(String path, String message) {
        this.path = path;
        this.message = message;
    }

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
