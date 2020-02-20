package ua.cruise.company.entity;

import java.math.BigDecimal;

public class Excursion {
    private Long id;
    private String nameEn;
    private String nameUkr;
    private String descriptionEn;
    private String descriptionUkr;
    private Long approximateDurationHr;
    private BigDecimal priceUSD;
    private Seaport seaport;

    public Excursion() {
        seaport = new Seaport();
    }

    public Excursion(Long id, String nameEn, String nameUkr, String descriptionEn, String descriptionUkr, Long approximateDurationHr, BigDecimal priceUSD, Seaport seaport) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameUkr = nameUkr;
        this.descriptionEn = descriptionEn;
        this.descriptionUkr = descriptionUkr;
        this.approximateDurationHr = approximateDurationHr;
        this.priceUSD = priceUSD;
        this.seaport = seaport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getApproximateDurationHr() {
        return approximateDurationHr;
    }

    public void setApproximateDurationHr(Long approximateDurationHr) {
        this.approximateDurationHr = approximateDurationHr;
    }

    public BigDecimal getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(BigDecimal priceUSD) {
        this.priceUSD = priceUSD;
    }

    public Seaport getSeaport() {
        return seaport;
    }

    public void setSeaport(Seaport seaport) {
        this.seaport = seaport;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameUkr='" + nameUkr + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", descriptionUkr='" + descriptionUkr + '\'' +
                ", approximateDurationHr=" + approximateDurationHr +
                ", priceUSD=" + priceUSD +
                ", seaport=" + seaport +
                '}';
    }
}
