package ua.cruise.company.utility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {
    private static final String MESSAGES_BUNDLE_NAME = "messages";

    public static BigDecimal getPriceInLocaleCurrency(BigDecimal priceInUSD) {
        if (priceInUSD == null) {
            return null;
        }

        BigDecimal localPriceMultiplier = new BigDecimal(getLocalizedMessage("localization.price.multiplier"));

        return localPriceMultiplier.multiply(priceInUSD);
    }

    public static String getDateInLocaleFormat(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    public static String getLocalizedMessage(String messageName) {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, currentLocale);
        return bundle.getString(messageName);
    }
}
