package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.entity.Extra;
import ua.training.cruise_company_servlet.web.dto.ExtraDTO;

import java.util.Locale;

public class ExtraDTOConverter {

    public static ExtraDTO convertToDTO(Extra extra){
        if(extra == null){
            return new ExtraDTO();
        }

        Locale currentLocale = Locale.getDefault();
        if(currentLocale.getLanguage().equalsIgnoreCase("uk"))
            return new ExtraDTO( extra.getId(),  extra.getNameUkr());

        return new ExtraDTO( extra.getId(),  extra.getNameEn());
    }
}
