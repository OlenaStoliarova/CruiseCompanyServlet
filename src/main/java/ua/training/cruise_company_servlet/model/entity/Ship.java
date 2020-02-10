package ua.training.cruise_company_servlet.model.entity;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    private Long id;
    private String name;
    private int capacity;
    private String routeNameEn;
    private String routeNameUkr;
    private int oneTripDurationDays;
    private Set<Seaport> visitingPorts = new HashSet<>();
    private Set<Extra> extras = new HashSet<>();

    public Ship() {
    }

    public Ship(Long id, String name, int capacity, String routeNameEn, String routeNameUkr, int oneTripDurationDays, Set<Seaport> visitingPorts, Set<Extra> extras) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.routeNameEn = routeNameEn;
        this.routeNameUkr = routeNameUkr;
        this.oneTripDurationDays = oneTripDurationDays;
        this.visitingPorts = visitingPorts;
        this.extras = extras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRouteNameEn() {
        return routeNameEn;
    }

    public void setRouteNameEn(String routeNameEn) {
        this.routeNameEn = routeNameEn;
    }

    public String getRouteNameUkr() {
        return routeNameUkr;
    }

    public void setRouteNameUkr(String routeNameUkr) {
        this.routeNameUkr = routeNameUkr;
    }

    public int getOneTripDurationDays() {
        return oneTripDurationDays;
    }

    public void setOneTripDurationDays(int oneTripDurationDays) {
        this.oneTripDurationDays = oneTripDurationDays;
    }

    public Set<Seaport> getVisitingPorts() {
        return visitingPorts;
    }

    public void setVisitingPorts(Set<Seaport> visitingPorts) {
        this.visitingPorts = visitingPorts;
    }

    public Set<Extra> getExtras() {
        return extras;
    }

    public void setExtras(Set<Extra> extras) {
        this.extras = extras;
    }
}
