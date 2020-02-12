package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.entity.Cruise;
import ua.training.cruise_company_servlet.utility.LocalizationHelper;
import ua.training.cruise_company_servlet.web.dto.CruiseDTO;

public class CruiseDTOConverter {

    public static CruiseDTO convertToDTO(Cruise cruise) {
        CruiseDTO dto = new CruiseDTO();

        dto.setId( cruise.getId() );
        dto.setStartingDate( LocalizationHelper.getDateInLocaleFormat(cruise.getStartingDate()) );
        dto.setVacancies( cruise.getVacancies());
        dto.setPrice( LocalizationHelper.getPriceInLocaleCurrency( cruise.getPrice()));
        dto.setShip( ShipDTOConverter.convertToDTO( cruise.getShip() ));

        return dto;
    }
}
