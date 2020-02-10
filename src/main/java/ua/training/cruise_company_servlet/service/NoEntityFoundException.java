package ua.training.cruise_company_servlet.service;

public class NoEntityFoundException extends Exception {
    public NoEntityFoundException(String message) {
        super(message);
    }
}
