package ua.cruise.company.web.form.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.utility.LocalizationHelper;
import ua.cruise.company.web.form.RegistrationForm;

public class RegistrationFormValidator {
    private static final Logger LOG = LogManager.getLogger(RegistrationFormValidator.class);

    public boolean isFormNotCompleted(RegistrationForm form){
        return form.getEmail() == null || form.getEmail().equals("") ||
                form.getPassword() == null || form.getPassword().equals("") ||
                form.getRepeatPassword() == null || form.getRepeatPassword().equals("") ||
                form.getFirstNameEn() == null || form.getFirstNameEn().equals("") ||
                form.getLastNameEn() == null || form.getLastNameEn().equals("") ||
                form.getFirstNameNative() == null || form.getFirstNameNative().equals("") ||
                form.getLastNameNative() == null || form.getLastNameNative().equals("");
    }

    public boolean isFormValid(RegistrationForm form){
        LOG.info("Validating RegistrationForm: " + form);
        boolean areAllFieldsValid =  isEmailValid(form.getEmail()) &&
                isPasswordValid(form.getPassword(), form.getRepeatPassword()) &&
                isNameEnValid(form.getFirstNameEn()) && isNameEnValid(form.getLastNameEn()) &&
                isNativeValid(form.getFirstNameNative()) && isNativeValid(form.getLastNameNative());
        if(areAllFieldsValid){
            LOG.info("RegistrationForm is correct");
        }
        return areAllFieldsValid;
    }

    private boolean isEmailValid(String email){
        String regexEmail = LocalizationHelper.getLocalizedMessage("regex.email");
        boolean result = email.matches(regexEmail);
        if(!result) {
            LOG.info("validation failed for email " + email);
        }
        return result;
    }

    private boolean isPasswordValid(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    private boolean isNameEnValid(String name){
        String regexNameEn = LocalizationHelper.getLocalizedMessage("regex.name.en");
        boolean result = name.matches(regexNameEn);
        if(!result) {
            LOG.info("validation failed for English name " + name);
        }
        return result;
    }

    private boolean isNativeValid(String name){
        return name.length() > 2;
    }

}
