package ua.training.cruise_company_servlet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.dao.DaoFactory;
import ua.training.cruise_company_servlet.dao.SeaportDao;
import ua.training.cruise_company_servlet.web.dto.SeaportDTO;
import ua.training.cruise_company_servlet.web.dto.converter.SeaportDTOConverter;
import ua.training.cruise_company_servlet.entity.Seaport;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class SeaportService {
    private static final Logger LOG = LogManager.getLogger(SeaportService.class);

    private final SeaportDao seaportDao = DaoFactory.getInstance().createSeaportDao();

    public boolean savePort(Seaport seaport) {
        LOG.info("Saving port " + seaport);
        return seaportDao.create(seaport);
    }

    public List<Seaport> getAllSeaports() {
        return seaportDao.findAll();
    }

    public List<SeaportDTO> getAllSeaportsLocalizedSorted() {
        List<Seaport> seaports = seaportDao.findAll();

        Collator currentLocaleCollator = Collator.getInstance(Locale.getDefault());
        return seaports.stream()
                .map(SeaportDTOConverter::convertToDTO)
                .sorted(comparing(SeaportDTO::getName, currentLocaleCollator))
                .collect(Collectors.toList());
    }
}
