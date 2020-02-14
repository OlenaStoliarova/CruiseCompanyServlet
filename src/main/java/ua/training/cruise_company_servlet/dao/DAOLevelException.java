package ua.training.cruise_company_servlet.dao;

public class DAOLevelException extends RuntimeException {
    public DAOLevelException(Throwable cause) {
        super(cause);
    }
    public DAOLevelException(String message){ super(message);}
}
