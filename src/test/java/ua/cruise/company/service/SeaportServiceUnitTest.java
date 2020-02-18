package ua.cruise.company.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.cruise.company.dao.SeaportDao;
import ua.cruise.company.entity.Seaport;
import ua.cruise.company.web.dto.SeaportDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeaportServiceUnitTest {

    @InjectMocks
    private SeaportService instance;

    @Mock
    private SeaportDao seaportDao;
    @Mock
    private Seaport seaport;

    @Test
    public void shouldReturnTrueWhenCreateSuccess(){
        when(seaportDao.create(any(Seaport.class))).thenReturn(Boolean.TRUE);

        boolean result = instance.savePort(seaport);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenCreateFail(){
        when(seaportDao.create(any(Seaport.class))).thenReturn(Boolean.FALSE);

        boolean result = instance.savePort(seaport);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnSomeListWhenFindAll() {
        when(seaportDao.findAll()).thenReturn(Collections.singletonList(seaport));

        List<Seaport> result = instance.getAllSeaports();

        assertThat(result).contains(seaport);
    }

    @Test
    public void shouldReturnSortedListLocaleDependant(){
        Seaport seaport1 = new Seaport();
        seaport1.setNameEn("Izmir");
        seaport1.setNameUkr("Ізмір");

        Seaport seaport2 = new Seaport();
        seaport2.setNameEn("Gdynia");
        seaport2.setNameUkr("Ґдиня");

        Seaport seaport3 = new Seaport();
        seaport3.setNameEn("Athens");
        seaport3.setNameUkr("Афіни");

        Seaport seaport4 = new Seaport();
        seaport4.setNameEn("Jamaica");
        seaport4.setNameUkr("Ямайка");

        Seaport seaport5 = new Seaport();
        seaport5.setNameEn("Trieste");
        seaport5.setNameUkr("Трієст");

        Seaport seaport6 = new Seaport();
        seaport6.setNameEn("Yitest");
        seaport6.setNameUkr("Їтест");

        List<Seaport> unsortedTestList = Arrays.asList(seaport1, seaport2, seaport3, seaport4, seaport5, seaport6);

        List<SeaportDTO> expectedWhenEnLocale = Arrays.asList(
                new SeaportDTO(null, "Athens", null),
                new SeaportDTO(null, "Gdynia", null),
                new SeaportDTO(null, "Izmir", null),
                new SeaportDTO(null, "Jamaica", null),
                new SeaportDTO(null, "Trieste", null),
                new SeaportDTO(null, "Yitest", null));

        List<SeaportDTO> expectedWhenUkrLocale = Arrays.asList(
                new SeaportDTO(null, "Афіни", null),
                new SeaportDTO(null, "Ґдиня", null),
                new SeaportDTO(null, "Ізмір", null),
                new SeaportDTO(null, "Їтест", null),
                new SeaportDTO(null, "Трієст", null),
                new SeaportDTO(null, "Ямайка", null));

        when(seaportDao.findAll()).thenReturn(unsortedTestList);

        //Testing with English locale
        Locale.setDefault(new Locale("en"));
        List<SeaportDTO> resultEn = instance.getAllSeaportsLocalizedSorted();

        assertThat(resultEn.size()).isEqualTo(expectedWhenEnLocale.size());

        for(int i=0; i < resultEn.size(); i++){
            assertThat(resultEn.get(i).getName())
                    .isEqualTo(expectedWhenEnLocale.get(i).getName());
        }

        //Testing with Ukraininan locale
        Locale.setDefault(new Locale("uk"));
        List<SeaportDTO> resultUkr = instance.getAllSeaportsLocalizedSorted();

        assertThat(resultUkr.size()).isEqualTo(expectedWhenUkrLocale.size());

        for(int i=0; i < resultUkr.size(); i++){
            assertThat(resultUkr.get(i).getName())
                    .isEqualTo(expectedWhenUkrLocale.get(i).getName());
        }
    }

}
