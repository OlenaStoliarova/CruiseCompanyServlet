package ua.cruise.company.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.dao.CruiseDao;
import ua.cruise.company.dao.DaoFactory;
import ua.cruise.company.entity.Cruise;
import ua.cruise.company.entity.Ship;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.dto.CruiseDTO;
import ua.cruise.company.web.dto.converter.CruiseDTOConverter;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CruiseService {
    private static final Logger LOG = LogManager.getLogger(CruiseService.class);

    private CruiseDao cruiseDao;
    private ShipService shipService;

    public CruiseService() {
        cruiseDao = DaoFactory.getInstance().createCruiseDao();
        shipService = new ShipService();
    }

    public Page<CruiseDTO> getAvailableCruisesAfterDate(LocalDate date, PaginationSettings paginationSettings) {
        Page<Cruise> cruises = cruiseDao.findAllAvailableAfterDate(date, paginationSettings);

        Iterator<Cruise> iterator = cruises.getContent().listIterator();
        while (iterator.hasNext()) {
            Cruise cruise = iterator.next();
            try {
                loadShip(cruise);
            } catch (NoEntityFoundException ex) {
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
        return CruiseDTOConverter.convertToDTO(getCruiseById(id));
    }

    public boolean update(Cruise cruise) {
        LOG.info("Updating cruise " + cruise);
        boolean isUpdated = cruiseDao.update(cruise);

        if (isUpdated) {
            LOG.info("Cruise was updated");
        } else {
            LOG.info("Cruise was not updated");
        }

        return isUpdated;
    }


    private void loadShip(Cruise cruise) throws NoEntityFoundException {
        Ship ship = shipService.getShipById(cruise.getShip().getId());
        cruise.setShip(ship);
    }

    private Page<CruiseDTO> getCruiseDTOPage(Page<Cruise> cruises, PaginationSettings paginationSettings) {
        List<CruiseDTO> contentDTO = cruises.getContent().stream()
                .map(CruiseDTOConverter::convertToDTO)
                .collect(Collectors.toList());
        return new Page<>(contentDTO, paginationSettings, cruises.getTotalElements());
    }
}
