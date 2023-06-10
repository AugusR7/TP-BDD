package com.bddabd.tp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "demand_on_date")
@NoArgsConstructor
public @Data class DemandOnDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_region_id")
    private Region region;

    @Column
    private Integer demand;

    @Column
    private Double temperature;

    public DemandOnDate(String date, Region region, Integer demand, Double temperature) {
        this.date = date;
        this.region = region;
        this.demand = demand;
        this.temperature = temperature;
    }

    @Override
    public String toString(){
        return "DemandOnDate{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", region=" + region +
                ", demand=" + demand +
                ", temperature=" + temperature +
                '}';
    }

}
