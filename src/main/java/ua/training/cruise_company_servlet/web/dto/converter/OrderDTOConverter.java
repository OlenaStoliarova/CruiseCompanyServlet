package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.entity.Excursion;
import ua.training.cruise_company_servlet.entity.Extra;
import ua.training.cruise_company_servlet.entity.Order;
import ua.training.cruise_company_servlet.enums.OrderStatus;
import ua.training.cruise_company_servlet.utility.LocalizationHelper;
import ua.training.cruise_company_servlet.web.dto.OrderDTO;

import java.util.Locale;
import java.util.Set;

public class OrderDTOConverter {

    public static OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();

        dto.setId( order.getId() );
        dto.setCreationDate( LocalizationHelper.getDateInLocaleFormat(order.getCreationDate()) );
        dto.setUser( UserDTOConverter.convertToDTO( order.getUser() ));
        dto.setCruise( CruiseDTOConverter.convertToDTO( order.getCruise()));
        dto.setQuantity( order.getQuantity());
        dto.setTotalPrice( LocalizationHelper.getPriceInLocaleCurrency( order.getTotalPrice()));
        dto.setStatus( order.getStatus().toString() );
        dto.setAddedExcursions( convertOrderExcursionsToString(order));
        dto.setFreeExtras( convertOrderFreeExtrasToString(order));

        return dto;
    }

    private static String convertOrderExcursionsToString(Order order){
        Locale currentLocale = Locale.getDefault();
        String lang = currentLocale.getLanguage();

        if( !order.getExcursions().isEmpty()){
            Set<Excursion> excursions = order.getExcursions();
            StringBuilder excursionsStr = new StringBuilder();
            for( Excursion excursion: excursions){
                if (lang.equalsIgnoreCase("uk"))
                    excursionsStr.append(excursion.getNameUkr());
                else
                    excursionsStr.append(excursion.getNameEn());
                excursionsStr.append(", ");
            }
            excursionsStr.replace( excursionsStr.lastIndexOf(","), excursionsStr.length(), "");

            return excursionsStr.toString();
        } else if (order.getStatus().compareTo(OrderStatus.EXCURSIONS_ADDED) >= 0){
            return "-";
        }
        return "";
    }

    private static String convertOrderFreeExtrasToString(Order order) {
        Locale currentLocale = Locale.getDefault();
        String lang = currentLocale.getLanguage();

        if (!order.getFreeExtras().isEmpty()) {
            Set<Extra> extras = order.getFreeExtras();
            StringBuilder extrasStr = new StringBuilder();
            for (Extra bonus : extras) {
                if (lang.equalsIgnoreCase("uk"))
                    extrasStr.append(bonus.getNameUkr());
                else
                    extrasStr.append(bonus.getNameEn());
                extrasStr.append(", ");
            }
            extrasStr.replace(extrasStr.lastIndexOf(","), extrasStr.length(), "");

            return extrasStr.toString();
        } else if (order.getStatus() == OrderStatus.EXTRAS_ADDED) {
            return "-";
        }
        return "";
    }
}
