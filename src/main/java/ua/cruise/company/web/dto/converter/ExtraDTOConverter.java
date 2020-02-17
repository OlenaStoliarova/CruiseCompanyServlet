package ua.cruise.company.web.dto.converter;

import ua.cruise.company.entity.Extra;
import ua.cruise.company.web.dto.ExtraDTO;

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
