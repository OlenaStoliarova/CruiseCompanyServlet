package ua.cruise.company.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.cruise.company.dao.CruiseDao;
import ua.cruise.company.entity.Cruise;
import ua.cruise.company.entity.Ship;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.dto.CruiseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CruiseServiceUnitTest {
    private static final Long ID = 1L;
    private static final Long NON_EXISTING_ID = 0L;
    private static final long DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final long DEFAULT_TOTAL_ELEMENTS = 13;

    @InjectMocks
    private CruiseService instance;

    @Mock
    private CruiseDao cruiseDao;
    @Mock
    private ShipService shipService;
    @Mock
    private Ship ship;

    private Cruise cruise;

    @Before
    public void setUp() throws NoEntityFoundException {
        cruise = new Cruise();
        cruise.setId(ID);
        cruise.getShip().setId(ID);

        when(cruiseDao.findById(ID)).thenReturn(Optional.of(cruise));
        when(cruiseDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        when(shipService.getShipById(ID)).thenReturn(ship);
    }

    @Test
    public void shouldReturnCruiseWhenFoundById() throws NoEntityFoundException {
        Cruise result = instance.getCruiseById(ID);

        assertThat(result).isEqualTo(cruise);
        assertThat(result.getShip()).isEqualTo(ship);
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenNotFoundById() throws NoEntityFoundException {
        instance.getCruiseById(NON_EXISTING_ID);
    }

    @Test
    public void shouldReturnCruiseDTOWhenFoundById() throws NoEntityFoundException {
        CruiseDTO result = instance.getCruiseDtoById(ID);

        assertThat(result.getId()).isEqualTo(cruise.getId());
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenDtoNotFoundById() throws NoEntityFoundException {
        instance.getCruiseDtoById(NON_EXISTING_ID);
    }

    @Test
    public void shouldReturnExpectedPageCruiseDTO() {
        PaginationSettings testPaginationSettings = new PaginationSettings(DEFAULT_PAGE_SIZE, DEFAULT_CURRENT_PAGE);
        List<Cruise> testContent = Collections.singletonList(cruise);
        Page<Cruise> testCruises = new Page<>(testContent, testPaginationSettings, DEFAULT_TOTAL_ELEMENTS);
        LocalDate testDate = LocalDate.now();

        when(cruiseDao.findAllAvailableAfterDate(testDate, testPaginationSettings)).thenReturn(testCruises);

        Page<CruiseDTO> result = instance.getAvailableCruisesAfterDate(testDate, testPaginationSettings);

        assertThat(result.getTotalElements()).isEqualTo(DEFAULT_TOTAL_ELEMENTS);
        assertThat(result.getSize()).isEqualTo(DEFAULT_PAGE_SIZE);
        assertThat(result.getCurrentPageNumber()).isEqualTo(DEFAULT_CURRENT_PAGE);
        assertThat(result.getContent().size()).isEqualTo(testContent.size());
        assertThat(result.getContent().get(0).getId()).isEqualTo(cruise.getId());
    }

    @Test
    public void shouldExcludeNotFullyLoadedItems() throws NoEntityFoundException {
        PaginationSettings testPaginationSettings = new PaginationSettings(DEFAULT_PAGE_SIZE, DEFAULT_CURRENT_PAGE);

        Cruise excludeTestCruise = new Cruise();
        excludeTestCruise.setId(ID);
        excludeTestCruise.getShip().setId(NON_EXISTING_ID);
        when(shipService.getShipById(NON_EXISTING_ID)).thenThrow(new NoEntityFoundException("test"));

        List<Cruise> testContent = new ArrayList<>();
        testContent.add(excludeTestCruise);
        testContent.add(cruise);
        Page<Cruise> testCruises = new Page<>(testContent, testPaginationSettings, DEFAULT_TOTAL_ELEMENTS);
        LocalDate testDate = LocalDate.now();
        when(cruiseDao.findAllAvailableAfterDate(testDate, testPaginationSettings)).thenReturn(testCruises);

        Page<CruiseDTO> result = instance.getAvailableCruisesAfterDate(testDate, testPaginationSettings);

        assertThat(result.getTotalElements()).isEqualTo(DEFAULT_TOTAL_ELEMENTS);
        assertThat(result.getSize()).isEqualTo(DEFAULT_PAGE_SIZE);
        assertThat(result.getCurrentPageNumber()).isEqualTo(DEFAULT_CURRENT_PAGE);
        assertThat(result.getContent().size()).isEqualTo(1); //initial testContent size -1
    }
}
