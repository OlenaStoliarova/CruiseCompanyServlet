package ua.cruise.company.web.dto.converter;

import ua.cruise.company.entity.Seaport;
import ua.cruise.company.web.dto.SeaportDTO;

import java.util.Locale;

public class SeaportDTOConverter {

    public static SeaportDTO convertToDTO(Seaport seaport) {
        if (seaport == null) {
            return new SeaportDTO();
        }

        Locale currentLocale = Locale.getDefault();
        if (currentLocale.getLanguage().equalsIgnoreCase("uk"))
            return new SeaportDTO(seaport.getId(), seaport.getNameUkr(), seaport.getCountryUkr());

        return new SeaportDTO(seaport.getId(), seaport.getNameEn(), seaport.getCountryEn());
    }
}
