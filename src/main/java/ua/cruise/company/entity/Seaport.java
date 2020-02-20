package ua.cruise.company.entity;

public class Seaport {
    private Long id;
    private String nameEn;
    private String nameUkr;
    private String countryEn;
    private String countryUkr;

    public Seaport() {
    }

    public Seaport(Long id, String nameEn, String nameUkr, String countryEn, String countryUkr) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameUkr = nameUkr;
        this.countryEn = countryEn;
        this.countryUkr = countryUkr;
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

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryUkr() {
        return countryUkr;
    }

    public void setCountryUkr(String countryUkr) {
        this.countryUkr = countryUkr;
    }

    @Override
    public String toString() {
        return "Seaport{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                ", nameUkr='" + nameUkr + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", countryUkr='" + countryUkr + '\'' +
                '}';
    }
}
