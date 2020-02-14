package ua.training.cruise_company_servlet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.dao.CruiseDao;
import ua.training.cruise_company_servlet.dao.DaoFactory;
import ua.training.cruise_company_servlet.entity.Cruise;
import ua.training.cruise_company_servlet.entity.Ship;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;
import ua.training.cruise_company_servlet.web.dto.CruiseDTO;
import ua.training.cruise_company_servlet.web.dto.converter.CruiseDTOConverter;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CruiseService {
    private static final Logger LOG = LogManager.getLogger(CruiseService.class);

    private final CruiseDao cruiseDao = DaoFactory.getInstance().createCruiseDao();

    public Page<CruiseDTO> getAvailableCruisesAfterDate(LocalDate date, PaginationSettings paginationSettings) {
        Page<Cruise> cruises = cruiseDao.findAllAvailableAfterDate(date, paginationSettings);

        Iterator<Cruise> iterator = cruises.getContent().iterator();
        while (iterator.hasNext()) {
            Cruise cruise = iterator.next();
            try{
                loadShip(cruise);
            } catch (NoEntityFoundException ex){
                LOG.debug(ex.getMessage(), ex);
                iterator.remove();
            }
        }

        return getCruiseDTOPage(cruises, paginationSettings);
    }

    public Cruise getCruiseById(long id) throws NoEntityFoundException {
        Cruise cruise = cruiseDao.findById(id).orElseThrow(() -> new NoEntityFoundException("There is no cruise with provided id (" + id + ")"));
        loadShip(cruise);
        return cruise;
    }

    public CruiseDTO getCruiseDtoById(long id) throws NoEntityFoundException {
        return CruiseDTOConverter.convertToDTO( getCruiseById(id));
    }

    private void loadShip(Cruise cruise) throws NoEntityFoundException {
        Ship ship = new ShipService().getShipById(cruise.getShip().getId());
        cruise.setShip(ship);
    }

    private Page<CruiseDTO> getCruiseDTOPage(Page<Cruise> cruises, PaginationSettings paginationSettings) {
        List<CruiseDTO> contentDTO = cruises.getContent().stream()
                .map(CruiseDTOConverter::convertToDTO)
                .collect(Collectors.toList());
        return new Page<>(contentDTO, paginationSettings, cruises.getTotalElements());
    }
}
