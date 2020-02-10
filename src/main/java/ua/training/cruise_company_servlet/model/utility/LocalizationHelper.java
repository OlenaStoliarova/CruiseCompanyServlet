package ua.training.cruise_company_servlet.model.utility;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {
    public static final String MESSAGES_BUNDLE_NAME = "messages";

    public static BigDecimal getPriceInLocaleCurrency(BigDecimal priceInUSD){
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, currentLocale);
        BigDecimal localPriceMultiplier = new BigDecimal( bundle.getString("localization.price.multiplier"));

        return localPriceMultiplier.multiply( priceInUSD );
    }

    public static String getLocalizedMessage(String messageName){
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, currentLocale);
        return bundle.getString(messageName);
    }
}
