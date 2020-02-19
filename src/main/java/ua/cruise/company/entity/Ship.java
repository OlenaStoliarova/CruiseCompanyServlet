package ua.cruise.company.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ship {
    private Long id;
    private String name;
    private int capacity;
    private String routeNameEn;
    private String routeNameUkr;
    private int oneTripDurationDays;
    private List<Seaport> visitingPorts = new ArrayList<>();
    private List<Extra> extras = new ArrayList<>();

    public Ship() {
    }

    public Ship(Long id, String name, int capacity, String routeNameEn, String routeNameUkr, int oneTripDurationDays, List<Seaport> visitingPorts, List<Extra> extras) {
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

    public List<Seaport> getVisitingPorts() {
        return visitingPorts;
    }

    public void setVisitingPorts(List<Seaport> visitingPorts) {
        this.visitingPorts = visitingPorts;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", routeNameEn='" + routeNameEn + '\'' +
                ", routeNameUkr='" + routeNameUkr + '\'' +
                ", oneTripDurationDays=" + oneTripDurationDays +
                ", visitingPorts=" + visitingPorts.stream().map(Seaport::getId).collect(Collectors.toList()) +
                ", extras=" + extras.stream().map(Extra::getId).collect(Collectors.toList()) +
                '}';
    }
}
