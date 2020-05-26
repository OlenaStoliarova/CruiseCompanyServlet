package ua.cruise.company.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import ua.cruise.company.dao.DAOLevelException;
import ua.cruise.company.dao.DaoFactory;
import ua.cruise.company.dao.UserDao;
import ua.cruise.company.entity.User;
import ua.cruise.company.enums.UserRole;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.NonUniqueObjectException;
import ua.cruise.company.service.exception.UserNotFoundException;
import ua.cruise.company.web.dto.UserDTO;
import ua.cruise.company.web.dto.converter.UserDTOConverter;
import ua.cruise.company.web.form.RegistrationForm;

import java.util.List;
import java.util.function.Predicate;

public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private UserDao userDao;

    public UserService() {
        this.userDao = DaoFactory.getInstance().createUserDao();
    }

    public UserDTO checkUserOnLogin(String login, String password) throws UserNotFoundException {

        Predicate<User> isPasswordCorrect = userFromDB -> BCrypt.checkpw(password, userFromDB.getPassword());

        return userDao.findByEmail(login)
                .filter(isPasswordCorrect)
                .map(UserDTOConverter::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException("incorrect login credentials"));
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public boolean updateUserRole(String email, UserRole newRole) {
        try {
            return userDao.updateUserRole(email, newRole);
        } catch (DAOLevelException ex) {
            return false;
        }
    }

    public User getUserById(long id) throws NoEntityFoundException {
        return userDao.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no user with provided id (" + id + ")"));
    }

    public User getUserByEmail(String email) throws NoEntityFoundException {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new NoEntityFoundException("There is no user with provided email (" + email + ")"));
    }

    public User createUser(RegistrationForm registrationForm) throws NonUniqueObjectException, NoEntityFoundException {
        User user = createUserFromForm(registrationForm);
        LOG.info("Creating new user " + user);
        boolean isCreated = userDao.create(user);

        if (isCreated) {
            LOG.info("User was created successfully");
            return getUserByEmail(registrationForm.getEmail());
        } else {
            LOG.info("User wasn't created");
            throw new NonUniqueObjectException("User with such email already exists");
        }
    }


    private User createUserFromForm(RegistrationForm form) {
        User user = new User();

        user.setEmail(form.getEmail());
        user.setFirstNameEn(form.getFirstNameEn());
        user.setLastNameEn(form.getLastNameEn());
        user.setFirstNameNative(form.getFirstNameNative());
        user.setLastNameNative(form.getLastNameNative());
        user.setRole(UserRole.ROLE_TOURIST);
        user.setPassword(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));

        return user;
    }
}
