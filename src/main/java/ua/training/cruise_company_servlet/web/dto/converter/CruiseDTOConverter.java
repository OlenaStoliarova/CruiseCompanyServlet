package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.entity.Cruise;
import ua.training.cruise_company_servlet.utility.LocalizationHelper;
import ua.training.cruise_company_servlet.web.dto.CruiseDTO;

import java.time.LocalDate;

public class CruiseDTOConverter {

    public static CruiseDTO convertToDTO(Cruise cruise) {
        CruiseDTO dto = new CruiseDTO();

        if(cruise == null){
            return dto;
        }

        dto.setId( cruise.getId() );
        dto.setStartingDate( LocalizationHelper.getDateInLocaleFormat(cruise.getStartingDate()) );
        dto.setFinishingDate( LocalizationHelper.getDateInLocaleFormat(calculateFinishingDate(cruise)));
        dto.setVacancies( cruise.getVacancies());
        dto.setPrice( LocalizationHelper.getPriceInLocaleCurrency( cruise.getPrice()));
        dto.setShip( ShipDTOConverter.convertToDTO( cruise.getShip() ));

        return dto;
    }

    private static LocalDate calculateFinishingDate(Cruise cruise){
        try {
            long tripDuration = cruise.getShip().getOneTripDurationDays();
            return cruise.getStartingDate().plusDays(tripDuration);
        } catch (NullPointerException ignored){
            return null;
        }
    }
}
