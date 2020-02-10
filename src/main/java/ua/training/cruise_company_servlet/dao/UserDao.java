package ua.training.cruise_company_servlet.dao;

import ua.training.cruise_company_servlet.entity.User;
import ua.training.cruise_company_servlet.enums.UserRole;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
    boolean updateUserRole(String email, UserRole newRole);
}
