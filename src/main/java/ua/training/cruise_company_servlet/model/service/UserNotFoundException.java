package ua.training.cruise_company_servlet.model.service;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
