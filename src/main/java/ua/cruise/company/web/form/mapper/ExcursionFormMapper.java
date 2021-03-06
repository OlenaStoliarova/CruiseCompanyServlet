package ua.cruise.company.web.form.mapper;

import ua.cruise.company.entity.Excursion;
import ua.cruise.company.web.constant.AttributesConstants;
import ua.cruise.company.web.form.ExcursionForm;

import javax.servlet.http.HttpServletRequest;

public class ExcursionFormMapper implements RequestFormMapper<ExcursionForm>, EntityFormMapper<Excursion, ExcursionForm> {
    private ExcursionForm excursionForm;

    @Override
    public ExcursionForm fillForm(HttpServletRequest request) {
        excursionForm = new ExcursionForm();

        excursionForm.setNameEn(request.getParameter(AttributesConstants.EXCURSION_NAME_EN));
        excursionForm.setNameUkr(request.getParameter(AttributesConstants.EXCURSION_NAME_UKR));
        excursionForm.setDescriptionEn(request.getParameter(AttributesConstants.EXCURSION_DESCRIPTION_EN));
        excursionForm.setDescriptionUkr(request.getParameter(AttributesConstants.EXCURSION_DESCRIPTION_UKR));
        excursionForm.setApproximateDurationHr(request.getParameter(AttributesConstants.EXCURSION_DURATION));
        excursionForm.setPriceUSD(request.getParameter(AttributesConstants.EXCURSION_PRICE));
        excursionForm.setSeaportId(request.getParameter(AttributesConstants.EXCURSION_PORT));

        return excursionForm;
    }

    @Override
    public ExcursionForm fillForm(Excursion entity) {
        excursionForm = new ExcursionForm();

        excursionForm.setNameEn(entity.getNameEn());
        excursionForm.setNameUkr(entity.getNameUkr());
        excursionForm.setDescriptionEn(entity.getDescriptionEn());
        excursionForm.setDescriptionUkr(entity.getDescriptionUkr());
        excursionForm.setApproximateDurationHr(entity.getApproximateDurationHr().toString());
        excursionForm.setPriceUSD(entity.getPriceUSD().toString());
        excursionForm.setSeaportId(entity.getSeaport().getId().toString());

        return excursionForm;
    }
}
