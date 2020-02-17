package ua.cruise.company.dao;

import ua.cruise.company.entity.User;
import ua.cruise.company.enums.UserRole;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
    boolean updateUserRole(String email, UserRole newRole);
}
