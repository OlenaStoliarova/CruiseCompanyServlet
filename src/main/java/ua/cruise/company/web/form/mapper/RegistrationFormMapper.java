package ua.cruise.company.web.form.mapper;

import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.form.RegistrationForm;

import javax.servlet.http.HttpServletRequest;

public class RegistrationFormMapper implements RequestFormMapper<RegistrationForm> {

    @Override
    public RegistrationForm fillForm(HttpServletRequest request) {
        RegistrationForm registrationForm = new RegistrationForm();

        registrationForm.setEmail(request.getParameter(AttributesConstants.REGISTRATION_EMAIL));
        registrationForm.setPassword(request.getParameter(AttributesConstants.REGISTRATION_PASSWORD));
        registrationForm.setRepeatPassword(request.getParameter(AttributesConstants.REGISTRATION_REPEAT_PASSWORD));
        registrationForm.setFirstNameEn(request.getParameter(AttributesConstants.REGISTRATION_FIRST_NAME_EN));
        registrationForm.setLastNameEn(request.getParameter(AttributesConstants.REGISTRATION_LAST_NAME_EN));
        registrationForm.setFirstNameNative(request.getParameter(AttributesConstants.REGISTRATION_FIRST_NAME_NATIVE));
        registrationForm.setLastNameNative(request.getParameter(AttributesConstants.REGISTRATION_LAST_NAME_NATIVE));

        return registrationForm;
    }

}
