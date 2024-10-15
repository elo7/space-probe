package com.elo7.space_probe.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "probe")
public class Probe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Position position;

    @ManyToOne
    @JoinColumn(name = "planet_id", referencedColumnName = "id", nullable = false)
    private Planet planet;

    @Deprecated // hibernate only
    Probe() {}

    public Probe(String name, Position position, Planet planet) {
        this.name = name;
        this.position = position;
        this.planet = planet;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Integer getXPosition() {
        return getPosition().getX();
    }

    public Integer getYPosition() {
        return getPosition().getY();
    }

    public Integer getPlanetId() {
        return getPlanet().getId();
    }

}
