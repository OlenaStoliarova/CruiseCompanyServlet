package ua.training.cruise_company_servlet.utility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    public static String getDateInLocaleFormat(LocalDate date){
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    public static String getLocalizedMessage(String messageName){
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, currentLocale);
        return bundle.getString(messageName);
    }
}
