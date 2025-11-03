package com.projectmanagement.exception;

public class ProjectNotFoundException extends Exception {

    // Default constructor
    public ProjectNotFoundException() {
        super("Project not found!");
    }

    // Constructor with custom message
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
