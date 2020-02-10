package ua.training.cruise_company_servlet.model.dao;

import ua.training.cruise_company_servlet.model.entity.User;
import ua.training.cruise_company_servlet.model.enums.UserRole;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
    boolean updateUserRole(String email, UserRole newRole);
}
