package ua.cruise.company.service;

import ua.cruise.company.dao.DaoFactory;
import ua.cruise.company.dao.ExtraDao;
import ua.cruise.company.dao.SeaportDao;
import ua.cruise.company.dao.ShipDao;
import ua.cruise.company.entity.Extra;
import ua.cruise.company.entity.Seaport;
import ua.cruise.company.entity.Ship;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.web.dto.ShipDTO;
import ua.cruise.company.web.dto.converter.ShipDTOConverter;

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
