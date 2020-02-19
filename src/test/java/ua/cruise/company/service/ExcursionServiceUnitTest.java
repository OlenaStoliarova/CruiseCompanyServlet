package ua.cruise.company.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.cruise.company.dao.ExcursionDao;
import ua.cruise.company.dao.SeaportDao;
import ua.cruise.company.entity.Excursion;
import ua.cruise.company.entity.Seaport;
import ua.cruise.company.service.exception.NoEntityFoundException;
import ua.cruise.company.service.exception.NonUniqueObjectException;
import ua.cruise.company.utility.Page;
import ua.cruise.company.utility.PaginationSettings;
import ua.cruise.company.web.dto.ExcursionDTO;
import ua.cruise.company.web.form.ExcursionForm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExcursionServiceUnitTest {
    private static final long DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;

    private static final String EXCURSION_NAME_EN = "excursion name";
    private static final String EXCURSION_NAME_UKR = "назва екскурсії";
    private static final String EXCURSION_DESCRIPTION_EN = "excursion description";
    private static final String EXCURSION_DESCRIPTION_UKR = "опис екскурсії";
    private static final String EXCURSION_DURATION_STR = "5";
    private static final Long EXCURSION_DURATION = 5L;
    private static final String EXCURSION_PRICE_STR = "25.25";
    private static final BigDecimal EXCURSION_PRICE = new BigDecimal("25.25");
    private static final String ID_STR = "1";
    private static final Long ID = 1L;
    private static final Long ID2 = 22L;
    private static final String NON_EXISTING_ID_SRT = "0";
    private static final Long NON_EXISTING_ID = 0L;


    @InjectMocks
    private ExcursionService instance;

    @Mock
    private ExcursionDao excursionDao;
    @Mock
    private SeaportDao seaportDao;
    @Mock
    private Seaport seaport;
    @Mock
    private Excursion excursion;
    @Mock
    private Page<Excursion> excursionsPage;

    private ExcursionForm excursionForm;
    PaginationSettings paginationSettings = new PaginationSettings(DEFAULT_PAGE_SIZE, DEFAULT_CURRENT_PAGE);

    @Before
    public void setUp() {
        excursionForm = new ExcursionForm();
        excursionForm.setNameEn(EXCURSION_NAME_EN);
        excursionForm.setNameUkr(EXCURSION_NAME_UKR);
        excursionForm.setDescriptionEn(EXCURSION_DESCRIPTION_EN);
        excursionForm.setDescriptionUkr(EXCURSION_DESCRIPTION_UKR);
        excursionForm.setApproximateDurationHr(EXCURSION_DURATION_STR);
        excursionForm.setPriceUSD(EXCURSION_PRICE_STR);
        excursionForm.setSeaportId(ID_STR);

        when(seaportDao.findById(ID)).thenReturn(Optional.of(seaport));
        when(seaportDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        when(excursionDao.findById(ID)).thenReturn(Optional.of(excursion));
        when(excursionDao.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());
    }

    @Test
    public void shouldReturnExcursionWithFieldsFromForm() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.create(any(Excursion.class))).thenReturn(Boolean.TRUE);

        instance.saveExcursion(excursionForm);

        ArgumentCaptor<Excursion> argument = ArgumentCaptor.forClass(Excursion.class);
        verify(excursionDao).create(argument.capture());
        assertThat(argument.getValue().getNameEn()).isEqualTo(EXCURSION_NAME_EN);
        assertThat(argument.getValue().getNameUkr()).isEqualTo(EXCURSION_NAME_UKR);
        assertThat(argument.getValue().getDescriptionEn()).isEqualTo(EXCURSION_DESCRIPTION_EN);
        assertThat(argument.getValue().getDescriptionUkr()).isEqualTo(EXCURSION_DESCRIPTION_UKR);
        assertThat(argument.getValue().getApproximateDurationHr()).isEqualTo(EXCURSION_DURATION);
        assertThat(argument.getValue().getPriceUSD()).isEqualTo(EXCURSION_PRICE);
        assertThat(argument.getValue().getSeaport().getId()).isEqualTo(ID);
    }

    @Test
    public void shouldReturnVoidAndNoExceptionWhenCreateSuccess() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.create(any(Excursion.class))).thenReturn(Boolean.TRUE);

        instance.saveExcursion(excursionForm);
    }

    @Test (expected = NonUniqueObjectException.class)
    public void shouldThrowNonUniqueObjectExceptionWhenCreateFail() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.create(any(Excursion.class))).thenReturn(Boolean.FALSE);
        instance.saveExcursion(excursionForm);
    }

    @Test (expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenCreateFailBecauseOfPort() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.create(any(Excursion.class))).thenReturn(Boolean.FALSE);
        excursionForm.setSeaportId(NON_EXISTING_ID_SRT);
        instance.saveExcursion(excursionForm);
    }

    @Test
    public void shouldReturnExcursionWithFieldsFromFormPlusId() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.update(any(Excursion.class))).thenReturn(Boolean.TRUE);

        instance.editExcursion(ID, excursionForm);

        ArgumentCaptor<Excursion> argument = ArgumentCaptor.forClass(Excursion.class);
        verify(excursionDao).update(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(ID);
        assertThat(argument.getValue().getNameEn()).isEqualTo(EXCURSION_NAME_EN);
        assertThat(argument.getValue().getNameUkr()).isEqualTo(EXCURSION_NAME_UKR);
        assertThat(argument.getValue().getDescriptionEn()).isEqualTo(EXCURSION_DESCRIPTION_EN);
        assertThat(argument.getValue().getDescriptionUkr()).isEqualTo(EXCURSION_DESCRIPTION_UKR);
        assertThat(argument.getValue().getApproximateDurationHr()).isEqualTo(EXCURSION_DURATION);
        assertThat(argument.getValue().getPriceUSD()).isEqualTo(EXCURSION_PRICE);
        assertThat(argument.getValue().getSeaport().getId()).isEqualTo(ID);
    }

    @Test
    public void shouldReturnVoidAndNoExceptionWhenUpdateSuccess() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.update(any(Excursion.class))).thenReturn(Boolean.TRUE);
        instance.editExcursion(ID, excursionForm);
    }

    @Test (expected = NonUniqueObjectException.class)
    public void shouldThrowNonUniqueObjectExceptionWhenUpdateFail() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.update(any(Excursion.class))).thenReturn(Boolean.FALSE);
        instance.editExcursion(ID, excursionForm);
    }

    @Test (expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenCreateUpdateBecauseOfPort() throws NonUniqueObjectException, NoEntityFoundException {
        when(excursionDao.update(any(Excursion.class))).thenReturn(Boolean.FALSE);
        excursionForm.setSeaportId(NON_EXISTING_ID_SRT);
        instance.editExcursion(ID, excursionForm);
    }

    @Test
    public void  shouldReturnTrueWhenDeleteSuccess(){
        when(excursionDao.delete(any(Long.class))).thenReturn(Boolean.TRUE);

        boolean result = instance.deleteExcursion(ID);

        assertThat(result).isTrue();
    }

    @Test
    public void  shouldReturnFalseWhenDeleteFail(){
        when(excursionDao.delete(any(Long.class))).thenReturn(Boolean.FALSE);

        boolean result = instance.deleteExcursion(ID);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnExcursionWhenFoundById() throws NoEntityFoundException {
        Excursion result = instance.getExcursionById(ID);

        assertThat(result).isEqualTo(excursion);
    }

    @Test( expected = NoEntityFoundException.class)
    public void shouldThrowNoEntityFoundExceptionWhenNotFoundById() throws NoEntityFoundException {
        instance.getExcursionById(NON_EXISTING_ID);
    }

    @Test
    public void shouldUseDaoForTheTask(){
        when(excursionDao.findAllOrderByPrice(paginationSettings)).thenReturn(excursionsPage);
        instance.getAllExcursionsForTravelAgent(paginationSettings);
        verify(excursionDao).findAllOrderByPrice(paginationSettings);

        when(excursionDao.findBySeaportIdOrderByNameEn(ID, paginationSettings)).thenReturn(excursionsPage);
        instance.getAllExcursionsForTravelAgent(ID, paginationSettings);
        verify(excursionDao).findBySeaportIdOrderByNameEn(ID, paginationSettings);
    }


    @Test
    public void shouldReturnExpectedDtoList(){
        Long portId1 = 1L;
        Long portId2 = 2L;
        List<Long> portIds = Arrays.asList(portId1, portId2);

        Seaport port1 = new Seaport();
        port1.setId(portId1);
        Seaport port2 = new Seaport();
        port2.setId(portId2);

        Excursion excursion1 = new Excursion();
        excursion1.setId(ID);
        excursion1.setSeaport(port1);
        Excursion excursion2 = new Excursion();
        excursion2.setId(ID2);
        excursion2.setSeaport(port2);

        List<Excursion> excursions = new ArrayList<>(2);
        excursions.add(excursion1);
        excursions.add(excursion2);

        when(excursionDao.findAllBySeaportIds(portIds)).thenReturn(excursions);
        when(seaportDao.findById(portId1)).thenReturn(Optional.of(port1));
        when(seaportDao.findById(portId2)).thenReturn(Optional.of(port2));

        List<ExcursionDTO> result = instance.getAllExcursionBySeaportIds(portIds);

        assertThat(result.get(0).getId()).isEqualTo(excursion1.getId());
        assertThat(result.get(0).getSeaport().getId()).isEqualTo(portId1);
        assertThat(result.get(1).getId()).isEqualTo(excursion2.getId());
        assertThat(result.get(1).getSeaport().getId()).isEqualTo(portId2);
    }

}
