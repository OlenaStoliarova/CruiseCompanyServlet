package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.web.dto.ExcursionForTravelAgentDTO;
import ua.training.cruise_company_servlet.entity.Excursion;
import ua.training.cruise_company_servlet.utility.LocalizationHelper;

public class ExcursionDTOConverter {

    public static ExcursionForTravelAgentDTO convertToDTOForTravelAgent(Excursion excursion) {
        ExcursionForTravelAgentDTO dto = new ExcursionForTravelAgentDTO();

        dto.setId( excursion.getId() );
        dto.setNameEn( excursion.getNameEn() );
        dto.setNameUkr( excursion.getNameUkr() );
        dto.setDescriptionEn( excursion.getDescriptionEn() );
        dto.setDescriptionUkr( excursion.getDescriptionUkr() );
        dto.setApproximateDurationHr( excursion.getApproximateDurationHr() );
        dto.setPrice( LocalizationHelper.getPriceInLocaleCurrency( excursion.getPriceUSD()));
        dto.setSeaport( SeaportDTOConverter.convertToDTO( excursion.getSeaport()));

        return dto;
    }
}
