package ua.cruise.company.web.command;

import ua.cruise.company.service.UserService;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.NonUniqueObjectException;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.constant.PathConstants;
import ua.cruise.company.web.form.RegistrationForm;
import ua.cruise.company.web.form.mapper.RegistrationFormMapper;
import ua.cruise.company.web.form.validator.RegistrationFormValidator;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        RegistrationForm registrationForm = new RegistrationFormMapper().fillForm(request);

        RegistrationFormValidator validator = new RegistrationFormValidator();
        if (validator.isFormNotCompleted(registrationForm)) {
            request.setAttribute(AttributesConstants.REGISTRATION_USER, registrationForm);
            return PathConstants.REGISTRATION_JSP;
        }

        if (!validator.isFormValid(registrationForm)) {
            request.setAttribute(AttributesConstants.REGISTRATION_USER, registrationForm);
            request.setAttribute(AttributesConstants.ERROR_VALIDATION, true);
            return PathConstants.REGISTRATION_JSP;
        }

        try {
            new UserService().createUser(registrationForm);
        } catch (NonUniqueObjectException e) {
            request.setAttribute(AttributesConstants.REGISTRATION_USER, registrationForm);
            request.setAttribute(AttributesConstants.ERROR_NON_UNIQUE, true);
            return PathConstants.REGISTRATION_JSP;
        } catch (NoEntityFoundException e) {
            request.setAttribute(AttributesConstants.REGISTRATION_USER, registrationForm);
            request.setAttribute(AttributesConstants.ERROR_REGISTARTION_ERROR, true);
            return PathConstants.REGISTRATION_JSP;
        }

        return PathConstants.REDIRECT_PREFIX + PathConstants.SERVLET_PATH + PathConstants.REGISTRATION_SUCCESS_COMMAND;
    }
}
