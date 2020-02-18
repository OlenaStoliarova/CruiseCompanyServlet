package ua.cruise.company.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.cruise.company.dao.ExtraDao;
import ua.cruise.company.dao.SeaportDao;
import ua.cruise.company.dao.ShipDao;
import ua.cruise.company.entity.Extra;
import ua.cruise.company.entity.Seaport;
import ua.cruise.company.entity.Ship;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.web.dto.ShipDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShipServiceUnitTest {

    private static final Long ID = 1L;
    private static final Long NON_EXISTING_ID = 0L;

    @InjectMocks
    private ShipService instance;

    @Mock
    private ShipDao shipDao;
    @Mock
    private ExtraDao extraDao;
    @Mock
    private SeaportDao seaportDao;

    private Ship ship;
    private List<Extra> extras;
    private List<Seaport> seaports;


    @Before
    public void setUp() {
        ship = new Ship();
        ship.setId(ID);

        when(shipDao.findById(ID)).thenReturn(Optional.of(ship));
        when(shipDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        extras = Collections.singletonList(new Extra());
        seaports = Collections.singletonList(new Seaport());
        when(extraDao.findAllByShipId(ID)).thenReturn(extras);
        when(seaportDao.findAllOfShipRoute(ID)).thenReturn(seaports);
    }

    @Test
    public void shouldReturnShipWhenFoundById() throws NoEntityFoundException {
        Ship result = instance.getShipById(ID);

        assertThat(result).isEqualTo(ship);
        assertThat(result.getExtras()).isEqualTo(extras);
        assertThat(result.getVisitingPorts()).isEqualTo(seaports);
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenNotFoundById() throws NoEntityFoundException {
        instance.getShipById(NON_EXISTING_ID);
    }

    @Test
    public void shouldReturnSomeListWhenFindAll() {
        when(shipDao.findAll()).thenReturn(Collections.singletonList(ship));

        List<ShipDTO> result = instance.getAllShips();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(ship.getId());
    }
}
