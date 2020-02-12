package ua.training.cruise_company_servlet.web.dto;

import java.math.BigDecimal;

public class CruiseDTO {
    private Long id;
    private String startingDate;
    private ShipDTO ship;
    private BigDecimal price;
    private int vacancies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public ShipDTO getShip() {
        return ship;
    }

    public void setShip(ShipDTO ship) {
        this.ship = ship;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public String toString() {
        return "CruiseDTO{" +
                "id=" + id +
                ", startingDate=" + startingDate +
                ", ship=" + ship +
                ", price=" + price +
                ", vacancies=" + vacancies +
                '}';
    }
}
