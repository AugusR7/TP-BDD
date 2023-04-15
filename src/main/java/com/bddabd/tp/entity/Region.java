package com.bddabd.tp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "CountryRegion")
public class Region {
    @Id
    @Column(name = "CountryRegion_Id")
    @Getter
    private Integer id;

    @Column(name = "CountryRegion_Name")
    @Getter
    private String name;

    public Region() {
    }

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
