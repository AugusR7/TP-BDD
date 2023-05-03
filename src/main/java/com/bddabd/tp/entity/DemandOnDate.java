package com.bddabd.tp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "demand_on_date")
//@AllArgsConstructor
@NoArgsConstructor
public @Data class DemandOnDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private String date;

    // Lazy porque cuando queremos ver la demanda, se hará con varias demandas a la vez, y no necesariamente vamos a traer a la region (?)
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

}
