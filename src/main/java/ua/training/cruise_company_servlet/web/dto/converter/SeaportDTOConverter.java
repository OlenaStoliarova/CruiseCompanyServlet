package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.web.dto.SeaportDTO;
import ua.training.cruise_company_servlet.entity.Seaport;

import java.util.Locale;

public class SeaportDTOConverter {

    public static SeaportDTO convertToDTO(Seaport seaport){
        if(seaport == null){
            return new SeaportDTO();
        }

        Locale currentLocale = Locale.getDefault();
        if(currentLocale.getLanguage().equalsIgnoreCase("uk"))
            return new SeaportDTO( seaport.getId(),  seaport.getNameUkr(),  seaport.getCountryUkr());

        return new SeaportDTO( seaport.getId(),  seaport.getNameEn(),  seaport.getCountryEn());
    }
}
