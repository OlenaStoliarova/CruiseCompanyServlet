package ua.training.cruise_company_servlet.service;

public class IllegalRequestException extends Exception {
    public IllegalRequestException(String message) {
        super(message);
    }
}
