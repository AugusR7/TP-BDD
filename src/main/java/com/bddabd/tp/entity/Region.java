package com.bddabd.tp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "country_region")
@NoArgsConstructor

public class Region implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
//    @Column(name = "demand_on_date")
//    private List<DemandOnDate> demandOnDate = new ArrayList<>();


    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
