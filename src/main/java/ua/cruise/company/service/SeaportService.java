package ua.cruise.company.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.cruise.company.dao.DaoFactory;
import ua.cruise.company.dao.SeaportDao;
import ua.cruise.company.entity.Seaport;
import ua.cruise.company.web.dto.SeaportDTO;
import ua.cruise.company.web.dto.converter.SeaportDTOConverter;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class SeaportService {
    private static final Logger LOG = LogManager.getLogger(SeaportService.class);

    private SeaportDao seaportDao;

    public SeaportService() {
        this.seaportDao = DaoFactory.getInstance().createSeaportDao();
    }

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
