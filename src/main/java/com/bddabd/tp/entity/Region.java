package com.bddabd.tp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "country_region")
//@AllArgsConstructor
@NoArgsConstructor
public @Data class Region {
    @Id
    @Column(name = "country_region_id")
    private Integer id;

    @Column(name = "country_region_name")
    private String name;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    @Getter
    private List<DemandOnDate> demandOnDate = new ArrayList<>();


    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Region))
            return false;
        Region region = (Region) o;
        return Objects.equals(this.id, region.id) && Objects.equals(this.name, region.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "CountryRegion{"
                + "id=" + this.id
                + ", name='" + this.name
                + '}';
    }
}
