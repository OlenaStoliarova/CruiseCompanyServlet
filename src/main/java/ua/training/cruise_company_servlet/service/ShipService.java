package ua.training.cruise_company_servlet.service;

import ua.training.cruise_company_servlet.dao.DaoFactory;
import ua.training.cruise_company_servlet.dao.ExtraDao;
import ua.training.cruise_company_servlet.dao.SeaportDao;
import ua.training.cruise_company_servlet.dao.ShipDao;
import ua.training.cruise_company_servlet.entity.Extra;
import ua.training.cruise_company_servlet.entity.Seaport;
import ua.training.cruise_company_servlet.entity.Ship;
import ua.training.cruise_company_servlet.web.dto.ShipDTO;
import ua.training.cruise_company_servlet.web.dto.converter.ShipDTOConverter;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ShipService {
    private final ShipDao shipDao = DaoFactory.getInstance().createShipDao();
    private final ExtraDao extraDao = DaoFactory.getInstance().createExtraDao();
    private final SeaportDao seaportDao = DaoFactory.getInstance().createSeaportDao();

    public Ship getShipById(long id) throws NoEntityFoundException {
        Ship ship = shipDao.findById(id).orElseThrow(() -> new NoEntityFoundException("There is no ship with provided id (" + id + ")"));
        loadShipExtras(ship);
        loadSeaportsOfShipRoute(ship);
        return ship;
    }

    public List<ShipDTO> getAllShips() {
        List<Ship> ships= shipDao.findAll();
        for(Ship s : ships){
            loadShipExtras(s);
            loadSeaportsOfShipRoute(s);
        }

        return ships.stream()
                .map(ShipDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    private void loadSeaportsOfShipRoute(Ship ship){
        List<Seaport> seaports = seaportDao.findAllOfShipRoute(ship.getId());
        ship.setVisitingPorts(new HashSet<>(seaports));
    }

    private void loadShipExtras(Ship ship){
        List<Extra> extras = extraDao.findAllByShipId(ship.getId());
        ship.setExtras(new HashSet<>(extras));
    }
}
