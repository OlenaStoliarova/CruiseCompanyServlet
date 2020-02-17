package ua.cruise.company.dao;

public class DAOLevelException extends RuntimeException {
    public DAOLevelException(Throwable cause) {
        super(cause);
    }
    public DAOLevelException(String message){ super(message);}
}
