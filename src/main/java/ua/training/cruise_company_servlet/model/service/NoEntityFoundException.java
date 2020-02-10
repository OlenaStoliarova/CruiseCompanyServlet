package ua.training.cruise_company_servlet.model.service;

public class NoEntityFoundException extends Exception {
    public NoEntityFoundException(String message) {
        super(message);
    }
}
