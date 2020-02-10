package ua.training.cruise_company_servlet.web.form;

public class ExcursionForm {
    private String nameEn;
    private String nameUkr;
    private String descriptionEn;
    private String descriptionUkr;
    private String approximateDurationHr;
    private String priceUSD;
    private String seaportId;

    public ExcursionForm() {
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionUkr() {
        return descriptionUkr;
    }

    public void setDescriptionUkr(String descriptionUkr) {
        this.descriptionUkr = descriptionUkr;
    }

    public String getApproximateDurationHr() {
        return approximateDurationHr;
    }

    public void setApproximateDurationHr(String approximateDurationHr) {
        this.approximateDurationHr = approximateDurationHr;
    }

    public String getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(String priceUSD) {
        this.priceUSD = priceUSD;
    }

    public String getSeaportId() {
        return seaportId;
    }

    public void setSeaportId(String seaportId) {
        this.seaportId = seaportId;
    }

    @Override
    public String toString() {
        return "ExcursionForm{" +
                "nameEn='" + nameEn + '\'' +
                ", nameUkr='" + nameUkr + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", descriptionUkr='" + descriptionUkr + '\'' +
                ", approximateDurationHr='" + approximateDurationHr + '\'' +
                ", priceUSD='" + priceUSD + '\'' +
                ", seaportId='" + seaportId + '\'' +
                '}';
    }
}
