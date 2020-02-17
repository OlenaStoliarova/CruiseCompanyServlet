package ua.training.cruise_company_servlet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import ua.training.cruise_company_servlet.dao.DAOLevelException;
import ua.training.cruise_company_servlet.dao.DaoFactory;
import ua.training.cruise_company_servlet.dao.UserDao;
import ua.training.cruise_company_servlet.entity.User;
import ua.training.cruise_company_servlet.enums.UserRole;
import ua.training.cruise_company_servlet.service.exception.NoEntityFoundException;
import ua.training.cruise_company_servlet.service.exception.NonUniqueObjectException;
import ua.training.cruise_company_servlet.service.exception.UserNotFoundException;
import ua.training.cruise_company_servlet.web.dto.UserDTO;
import ua.training.cruise_company_servlet.web.dto.converter.UserDTOConverter;
import ua.training.cruise_company_servlet.web.form.RegistrationForm;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class);

    private final UserDao userDao = DaoFactory.getInstance().createUserDao();

    public UserDTO checkUserOnLogin(String login, String password) throws UserNotFoundException {

        Optional<User> userOptional = userDao.findByEmail(login);

        if (!userOptional.isPresent()) {
            LOG.info("login '" + login + "' not found in the DB");
            throw new UserNotFoundException("login not found in the DB");
        }

        User userFromDB = userOptional.get();
        LOG.info(userFromDB);

        // Check that an unencrypted password matches one that has
        // previously been hashed
        if (BCrypt.checkpw(password, userFromDB.getPassword())) {
            LOG.info("password is correct");
            return UserDTOConverter.convertToDTO(userFromDB);
        } else {
            LOG.error("entered password does not match the one from the DB");
            throw new UserNotFoundException("incorrect password");
        }
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
        return userDao.findByEmail( email)
                .orElseThrow(() -> new NoEntityFoundException("There is no user with provided email (" + email + ")"));
    }

    public User createUser(RegistrationForm registrationForm) throws NonUniqueObjectException, NoEntityFoundException {
        User user = createUserFromForm(registrationForm);
        LOG.info("Creating new user " + user);
        boolean isCreated = userDao.create(user);

        if(isCreated){
            LOG.info("User was created successfully");
            return getUserByEmail( registrationForm.getEmail());
        } else{
            LOG.info("User wasn't created");
            throw new NonUniqueObjectException("User with such email already exists");
        }
    }


    private User createUserFromForm(RegistrationForm form) {
        User user = new User();

        user.setEmail( form.getEmail());
        user.setFirstNameEn( form.getFirstNameEn());
        user.setLastNameEn( form.getLastNameEn());
        user.setFirstNameNative( form.getFirstNameNative());
        user.setLastNameNative( form.getLastNameNative());
        user.setRole(UserRole.ROLE_TOURIST);
        user.setPassword( BCrypt.hashpw( form.getPassword(), BCrypt.gensalt()));

        return user;
    }
}
