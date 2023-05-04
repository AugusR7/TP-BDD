package com.bddabd.tp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "country_region")
//@AllArgsConstructor
@NoArgsConstructor

public class Region {
    @Id
    @Column(name = "country_region_id")
    @Getter
    private Integer id;

    @Column(name = "country_region_name")
    private String name;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    @Getter
    private List<DemandOnDate> demandOnDate = new ArrayList<>();


    public Region(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Region region))
            return false;
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
    }*/
}
