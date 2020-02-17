package ua.training.cruise_company_servlet.service.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
