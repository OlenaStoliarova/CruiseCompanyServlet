package ua.cruise.company.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cruise {
    private Long id;
    private LocalDate startingDate;
    private Ship ship;
    private BigDecimal price;
    private int vacancies;

    public Cruise() {
        ship = new Ship();
    }

    public Cruise(Long id, LocalDate startingDate, Ship ship, BigDecimal price, int vacancies) {
        this.id = id;
        this.startingDate = startingDate;
        this.ship = ship;
        this.price = price;
        this.vacancies = vacancies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
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
}
