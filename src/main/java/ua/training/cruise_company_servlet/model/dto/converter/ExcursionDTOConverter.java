package ua.training.cruise_company_servlet.model.dto.converter;

import ua.training.cruise_company_servlet.model.dto.ExcursionForTravelAgentDTO;
import ua.training.cruise_company_servlet.model.entity.Excursion;
import ua.training.cruise_company_servlet.model.utility.LocalizationHelper;

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
