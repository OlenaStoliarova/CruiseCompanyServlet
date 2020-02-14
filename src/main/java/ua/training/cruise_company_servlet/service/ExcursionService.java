package ua.training.cruise_company_servlet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.cruise_company_servlet.dao.DaoFactory;
import ua.training.cruise_company_servlet.dao.ExcursionDao;
import ua.training.cruise_company_servlet.dao.SeaportDao;
import ua.training.cruise_company_servlet.entity.Excursion;
import ua.training.cruise_company_servlet.entity.Seaport;
import ua.training.cruise_company_servlet.utility.Page;
import ua.training.cruise_company_servlet.utility.PaginationSettings;
import ua.training.cruise_company_servlet.web.dto.ExcursionDTO;
import ua.training.cruise_company_servlet.web.dto.ExcursionForTravelAgentDTO;
import ua.training.cruise_company_servlet.web.dto.converter.ExcursionDTOConverter;
import ua.training.cruise_company_servlet.web.form.ExcursionForm;

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

    public Page<ExcursionForTravelAgentDTO> getAllExcursionsForTravelAgent(PaginationSettings paginationSettings) {
        Page<Excursion> excursions = excursionDao.findAllOrderByPrice(paginationSettings);
        return getExcursionForTravelAgentDTOPage(excursions, paginationSettings);
    }

    public Page<ExcursionForTravelAgentDTO> getAllExcursionsForTravelAgent(long seaportId, PaginationSettings paginationSettings) {
        Page<Excursion> excursions = excursionDao.findBySeaportIdOrderByNameEn(seaportId,paginationSettings);
        return getExcursionForTravelAgentDTOPage(excursions, paginationSettings);
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

    public List<ExcursionDTO> getAllExcursionBySeaportIds(List<Long> portIds){
        List<Excursion> excursions = excursionDao.findAllBySeaportIds(portIds);
        for( Excursion excursion : excursions){
            loadSeaport(excursion);
        }
        return excursions.stream()
                .map(ExcursionDTOConverter::convertToDTO)
                .collect(Collectors.toList());
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

    private void loadSeaport(Excursion excursion){
        Seaport seaport = seaportDao.findById(excursion.getSeaport().getId()).orElse(new Seaport());
        excursion.setSeaport(seaport);
    }

    private Page<ExcursionForTravelAgentDTO> getExcursionForTravelAgentDTOPage(Page<Excursion> excursionsPage, PaginationSettings paginationSettings){
        List<Excursion> excursions = excursionsPage.getContent();
        for( Excursion excursion : excursions){
            loadSeaport(excursion);
        }
        List<ExcursionForTravelAgentDTO> contentDTO = excursionsPage.getContent().stream()
                .map(ExcursionDTOConverter::convertToDTOForTravelAgent)
                .collect(Collectors.toList());
        return new Page<>(contentDTO, paginationSettings, excursionsPage.getTotalElements());
    }
}
