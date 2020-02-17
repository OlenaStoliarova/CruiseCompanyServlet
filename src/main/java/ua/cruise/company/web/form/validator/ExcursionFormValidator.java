package ua.cruise.company.web.form.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.web.form.ExcursionForm;
import ua.cruise.company.utility.LocalizationHelper;

import java.math.BigDecimal;

public class ExcursionFormValidator {
    private static final Logger LOG = LogManager.getLogger(ExcursionFormValidator.class);

    public boolean isFormNotCompleted(ExcursionForm form){
        return form.getNameEn() == null || form.getNameEn().equals("") ||
                form.getNameUkr()== null || form.getNameUkr().equals("") ||
                form.getDescriptionEn()== null || form.getDescriptionEn().equals("") ||
                form.getDescriptionUkr()== null || form.getDescriptionUkr().equals("") ||
                form.getApproximateDurationHr()== null || form.getApproximateDurationHr().equals("") ||
                form.getPriceUSD()== null || form.getPriceUSD().equals("") ||
                form.getSeaportId()== null || form.getSeaportId().equals("");
    }

    public boolean isFormValid(ExcursionForm form){
        LOG.info("Validating ExcursionForm: " + form);
        boolean areAllFieldsValid =  isNameEnValid(form.getNameEn()) && isNameUkrValid(form.getNameUkr())
                && isDescriptionValid(form.getDescriptionEn()) && isDescriptionValid(form.getDescriptionUkr())
                && isDurationValid(form.getApproximateDurationHr()) && isPriceValid(form.getPriceUSD());
        if(areAllFieldsValid){
            LOG.info("ExcursionForm is correct");
        }
        return areAllFieldsValid;
    }

    private boolean isNameEnValid(String name){
        String regexNameEn = LocalizationHelper.getLocalizedMessage("regex.excursion.name.en");
        boolean result = name.matches(regexNameEn);
        if(!result) {
            LOG.info("validation failed for field 'excursion name in English'");
        }
        return result;
    }

    private boolean isNameUkrValid(String name){
        String regexNameUkr = LocalizationHelper.getLocalizedMessage("regex.excursion.name.ukr");
        boolean result = name.matches(regexNameUkr);
        if(!result) {
            LOG.info("validation failed for field 'excursion name in Ukrainian'");
        }
        return result;
    }

    private boolean isDescriptionValid(String descr){
        if(descr.length() < 5){
            LOG.info("validation failed. excursion description is too short");
            return false;
        }
        return true;
    }

    private boolean isDurationValid(String duration){
        try {
            //excursions longer than three days are not expected
            if(Integer.parseInt(duration) > 72) {
                LOG.info("validation failed. excursion duration is too big");
                return false;
            }
        } catch (NumberFormatException ex){
            LOG.info("validation failed. excursion duration isn't number");
            return false;
        }
        return true;
    }

    private boolean isPriceValid(String strPrice){
        BigDecimal price;
        try {
            price = new BigDecimal(strPrice);
        } catch (NumberFormatException ex) {
            LOG.info("validation failed. excursion price isn't number");
            return false;
        }
        boolean isInExpectedRange = price.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(new BigDecimal(999999)) <= 0;
        if (!isInExpectedRange)
            LOG.info("validation failed. excursion price is out of expected range");
        return isInExpectedRange;
    }
}
