package ua.cruise.company.web.dto.converter;

import ua.cruise.company.entity.Ship;
import ua.cruise.company.web.dto.ExtraDTO;
import ua.cruise.company.web.dto.SeaportDTO;
import ua.cruise.company.web.dto.ShipDTO;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ShipDTOConverter {

    public static ShipDTO convertToDTO(Ship ship) {
        ShipDTO dto = new ShipDTO();

        if (ship == null) {
            return dto;
        }

        dto.setId(ship.getId());
        dto.setName(ship.getName());
        dto.setCapacity(ship.getCapacity());
        dto.setOneTripDurationDays(ship.getOneTripDurationDays());

        setLocaleSpecificFields(dto, ship);

        if (ship.getExtras() != null) {
            List<ExtraDTO> extras = ship.getExtras().stream()
                    .map(ExtraDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
            dto.setExtras(extras);
        }

        if (ship.getVisitingPorts() != null) {
            List<SeaportDTO> seaports = ship.getVisitingPorts().stream()
                    .map(SeaportDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
            dto.setVisitingPorts(seaports);
        }

        return dto;
    }

    private static void setLocaleSpecificFields(ShipDTO dto, Ship ship) {
        Locale currentLocale = Locale.getDefault();

        if (currentLocale.getLanguage().equalsIgnoreCase("uk")) {
            dto.setRouteName(ship.getRouteNameUkr());
            return;
        }

        dto.setRouteName(ship.getRouteNameEn());
    }
}
