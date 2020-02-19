package ua.cruise.company.entity;

public class Extra {
    private Long id;
    private String nameEn;
    private String nameUkr;

    public Extra() {
    }

    public Extra(Long id, String nameEn, String nameUkr) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameUkr = nameUkr;
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

    @Override
    public String toString() {
        return "Extra{" +
                "id=" + id +
                ", nameEn='" + nameEn + '\'' +
                '}';
    }
}
