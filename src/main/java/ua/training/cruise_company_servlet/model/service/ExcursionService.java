package ua.training.cruise_company_servlet.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.controller.form.ExcursionForm;
import ua.training.cruise_company_servlet.model.dao.DaoFactory;
import ua.training.cruise_company_servlet.model.dao.ExcursionDao;
import ua.training.cruise_company_servlet.model.dao.SeaportDao;
import ua.training.cruise_company_servlet.model.dto.ExcursionForTravelAgentDTO;
import ua.training.cruise_company_servlet.model.dto.converter.ExcursionDTOConverter;
import ua.training.cruise_company_servlet.model.entity.Excursion;
import ua.training.cruise_company_servlet.model.entity.Seaport;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ExcursionService {
    private static final Logger LOG = LogManager.getLogger(ExcursionService.class);

    private final ExcursionDao excursionDao = DaoFactory.getInstance().createExcursionDao();
    private final SeaportDao seaportDao = DaoFactory.getInstance().createSeaportDao();


    public void saveExcursion(ExcursionForm excursionForm) throws NoEntityFoundException, NonUniqueObjectException {
        Excursion excursion = createExcursionFromForm(excursionForm);
        LOG.info("Saving excursion " + excursion);
        boolean isCreated = excursionDao.create(excursion);

        if(isCreated){
            LOG.info("Excursion was saved (created)");
        } else{
            LOG.info("Excursion wasn't saved");
            Long portId = excursion.getSeaport().getId();
            seaportDao.findById(portId).orElseThrow(() -> new NoEntityFoundException("There is no port with provided id (" + portId + ")"));
            throw new NonUniqueObjectException("Excursion with such name already exists for this port");
        }
    }

    public List<ExcursionForTravelAgentDTO> getAllExcursionsForTravelAgent() {
        List<Excursion> excursions = excursionDao.findAll();

        return excursions.stream()
                .map(ExcursionDTOConverter::convertToDTOForTravelAgent)
                .collect(Collectors.toList());
    }

    public List<ExcursionForTravelAgentDTO> getAllExcursionsInSeaportForTravelAgent(long seaportId) {
        List<Excursion> excursions = excursionDao.findBySeaportId(seaportId);

        return excursions.stream()
                .map(ExcursionDTOConverter::convertToDTOForTravelAgent)
                .collect(Collectors.toList());
    }

    public boolean deleteExcursion(Long id){
        LOG.info("Deleting excursion " + id);
        boolean result = excursionDao.delete(id);
        if(result){
            LOG.info(" excursion was deleted");
        } else {
            LOG.info(" excursion was NOT deleted");
        }
        return result;
    }

    public Excursion getExcursionById(Long id) throws NoEntityFoundException {
        return excursionDao.findById(id)
                .orElseThrow(() -> new NoEntityFoundException("There is no excursion with provided id (" + id + ")"));
    }

    public void editExcursion(Long excursionId, ExcursionForm excursionForm) throws NoEntityFoundException, NonUniqueObjectException {
        Excursion excursion = createExcursionFromForm(excursionForm);
        excursion.setId(excursionId);
        LOG.info("Editing excursion " + excursion);
        boolean isUpdated = excursionDao.update(excursion);

        if(isUpdated){
            LOG.info("Excursion was updated");
        } else{
            LOG.info("Excursion was not updated");
            Long portId = excursion.getSeaport().getId();
            seaportDao.findById(portId).orElseThrow(() -> new NoEntityFoundException("There is no port with provided id (" + portId + ")"));
            throw new NonUniqueObjectException("Excursion with such name already exists for this port");
        }
    }

    private Excursion createExcursionFromForm(ExcursionForm form){
        Excursion excursion = new Excursion();
        excursion.setNameEn(form.getNameEn());
        excursion.setNameUkr(form.getNameUkr());
        excursion.setDescriptionEn(form.getDescriptionEn());
        excursion.setDescriptionUkr(form.getDescriptionUkr());
        excursion.setApproximateDurationHr(Long.parseLong( form.getApproximateDurationHr()));
        excursion.setPriceUSD(new BigDecimal(form.getPriceUSD()));
        Seaport seaport = new Seaport();
        seaport.setId(Long.parseLong(form.getSeaportId()));
        excursion.setSeaport(seaport);
        return excursion;
    }
}
