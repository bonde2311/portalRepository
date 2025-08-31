package com.bsquare.exception;

public class JobPortalException extends Exception {
    
    private static final long serialVersionUID = 1L; // A unique identifier for serializing the exception object
    
    // Constructor that accepts a custom error message to be passed to the superclass (Exception)
    public JobPortalException(String message) {
        super(message); // Calls the parent class constructor with the custom message
    }
}
