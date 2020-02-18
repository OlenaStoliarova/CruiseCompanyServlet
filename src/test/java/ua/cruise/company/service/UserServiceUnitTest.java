package ua.cruise.company.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.cruise.company.dao.DAOLevelException;
import ua.cruise.company.dao.UserDao;
import ua.cruise.company.entity.User;
import ua.cruise.company.enums.UserRole;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.NonUniqueObjectException;
import ua.cruise.company.service.exception.UserNotFoundException;
import ua.cruise.company.web.dto.UserDTO;
import ua.cruise.company.web.form.RegistrationForm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

    private static final String USER_LOGIN = "user@login";
    private static final String USER_PASSWORD = "user password";
    private static final String INVALID_LOGIN = "invalid login";
    private static final String INVALID_PASSWORD = "invalid password";

    @InjectMocks
    private UserService instance;

    @Mock
    private UserDao userDao;
    @Mock
    private User user;

    @Before
    public void setUp(){
        when(user.getEmail()).thenReturn(USER_LOGIN);
        when(user.getPassword()).thenReturn(BCrypt.hashpw(USER_PASSWORD, BCrypt.gensalt()));

        when(userDao.findByEmail(USER_LOGIN)).thenReturn(Optional.of(user));
        when(userDao.findByEmail(INVALID_LOGIN)).thenReturn(Optional.empty());
    }

    @Test
    public void shouldReturnUserDTOWhenLoginAndPasswordValid() throws UserNotFoundException {
        UserDTO result = instance.checkUserOnLogin(USER_LOGIN, USER_PASSWORD);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(USER_LOGIN);
    }

    @Test (expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenLoginInvalid() throws UserNotFoundException {
        instance.checkUserOnLogin(INVALID_LOGIN, USER_PASSWORD);
    }

    @Test (expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenPasswordInvalid() throws UserNotFoundException {
        instance.checkUserOnLogin(USER_LOGIN, INVALID_PASSWORD);
    }

    @Test
    public void shouldReturnTrueWhenRoleUpdated(){
        when(userDao.updateUserRole(anyString(), any(UserRole.class))).thenReturn(Boolean.TRUE);

        boolean result = instance.updateUserRole(USER_LOGIN, UserRole.ROLE_TRAVEL_AGENT);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenRoleNotUpdated(){
        when(userDao.updateUserRole(anyString(), any(UserRole.class))).thenReturn(Boolean.FALSE);

        boolean result = instance.updateUserRole(USER_LOGIN, UserRole.ROLE_TRAVEL_AGENT);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenRoleNotUpdatedBecauseOfDAOLevelException(){
        when(userDao.updateUserRole(anyString(), any(UserRole.class))).thenThrow(new DAOLevelException("test"));

        boolean result = instance.updateUserRole(USER_LOGIN, UserRole.ROLE_TRAVEL_AGENT);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnSomeListWhenFindAll() {
        when(userDao.findAll()).thenReturn(Collections.singletonList(user));

        List<User> result = instance.getAllUsers();

        assertThat(result).contains(user);
    }

    @Test
    public void shouldReturnUserWhenFoundById() throws NoEntityFoundException {
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));

        User result = instance.getUserById(0L);

        assertThat(result).isEqualTo(user);
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenNotFoundById() throws NoEntityFoundException {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        instance.getUserById(0L);
    }

    @Test
    public void shouldReturnUserWhenFoundByEmail() throws NoEntityFoundException {
        User result = instance.getUserByEmail(USER_LOGIN);

        assertThat(result).isEqualTo(user);
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenNotFoundByEmail() throws NoEntityFoundException {
        instance.getUserByEmail(INVALID_LOGIN);
    }

    @Test
    public void shouldReturnUserWhenCreateSuccess() throws NonUniqueObjectException, NoEntityFoundException {
        when(userDao.create(any(User.class))).thenReturn(Boolean.TRUE);

        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setEmail(USER_LOGIN);
        registrationForm.setPassword(USER_PASSWORD);

        User result = instance.createUser(registrationForm);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(user);
    }

    @Test (expected = NonUniqueObjectException.class)
    public void shouldThrowNonUniqueObjectExceptionWhenCreateFail() throws NonUniqueObjectException, NoEntityFoundException {
        when(userDao.create(any(User.class))).thenReturn(Boolean.FALSE);
        instance.createUser(new RegistrationForm());
    }
}