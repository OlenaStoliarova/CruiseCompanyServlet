package ua.cruise.company.entity;

import ua.cruise.company.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private Long id;
    private LocalDate creationDate;
    private User user;
    private Cruise cruise;
    private int quantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<Excursion> excursions = new ArrayList<>();
    private List<Extra> freeExtras = new ArrayList<>();

    public Order() {
        user = new User();
        cruise = new Cruise();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }

    public List<Extra> getFreeExtras() {
        return freeExtras;
    }

    public void setFreeExtras(List<Extra> freeExtras) {
        this.freeExtras = freeExtras;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", user=" + user.getId() +
                ", cruise=" + cruise.getId() +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", excursions=" + excursions.stream().map(Excursion::getId).collect(Collectors.toList()) +
                ", freeExtras=" + freeExtras.stream().map(Extra::getId).collect(Collectors.toList()) +
                '}';
    }
}
