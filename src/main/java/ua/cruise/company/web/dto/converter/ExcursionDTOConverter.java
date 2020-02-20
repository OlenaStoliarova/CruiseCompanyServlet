package ua.cruise.company.web.dto.converter;

import ua.cruise.company.entity.Excursion;
import ua.cruise.company.utility.LocalizationHelper;
import ua.cruise.company.web.dto.ExcursionDTO;

import java.util.Locale;

public class ExcursionDTOConverter {

    public static ExcursionDTO convertToDTO(Excursion excursion) {
        ExcursionDTO dto = new ExcursionDTO();

        if (excursion == null) {
            return dto;
        }

        dto.setId(excursion.getId());
        setLocaleSpecificFields(dto, excursion);
        dto.setApproximateDurationHr(excursion.getApproximateDurationHr());
        dto.setPrice(LocalizationHelper.getPriceInLocaleCurrency(excursion.getPriceUSD()));
        dto.setSeaport(SeaportDTOConverter.convertToDTO(excursion.getSeaport()));

        return dto;
    }

    private static void setLocaleSpecificFields(ExcursionDTO dto, Excursion excursion) {
        Locale currentLocale = Locale.getDefault();

        if (currentLocale.getLanguage().equalsIgnoreCase("uk")) {
            dto.setName(excursion.getNameUkr());
            dto.setDescription(excursion.getDescriptionUkr());
            return;
        }

        dto.setName(excursion.getNameEn());
        dto.setDescription(excursion.getDescriptionEn());
    }
}
