package ua.training.cruise_company_servlet.web.dto;

import java.math.BigDecimal;

public class ExcursionForTravelAgentDTO {
    private Long id;
    private String nameEn;
    private String nameUkr;
    private String descriptionEn;
    private String descriptionUkr;
    private Long approximateDurationHr;
    private BigDecimal price;
    private SeaportDTO seaport;

    public Long getId() {
        return id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameUkr() {
        return nameUkr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionUkr() {
        return descriptionUkr;
    }

    public Long getApproximateDurationHr() {
        return approximateDurationHr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SeaportDTO getSeaport() {
        return seaport;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public void setDescriptionUkr(String descriptionUkr) {
        this.descriptionUkr = descriptionUkr;
    }

    public void setApproximateDurationHr(Long approximateDurationHr) {
        this.approximateDurationHr = approximateDurationHr;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSeaport(SeaportDTO seaport) {
        this.seaport = seaport;
    }

    @Override
    public String toString() {
        return "ExcursionForTravelAgentDTO{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameUkr='" + nameUkr + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", descriptionUkr='" + descriptionUkr + '\'' +
                ", approximateDurationHr=" + approximateDurationHr +
                ", price=" + price +
                ", seaport=" + seaport +
                '}';
    }
}
