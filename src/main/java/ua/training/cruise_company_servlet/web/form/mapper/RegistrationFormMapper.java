package ua.training.cruise_company_servlet.web.form.mapper;

import ua.training.cruise_company_servlet.web.constant.AttributesConstants;
import ua.training.cruise_company_servlet.web.form.RegistrationForm;

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
