package com.bddabd.tp.dto;

import java.util.Objects;

public class RegionDTO {
    private Integer id;
    private String name;

    public RegionDTO() {
    }

    public RegionDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RegionDTO)) {
            return false;
        }
        RegionDTO regionDTO = (RegionDTO) o;
        return Objects.equals(id, regionDTO.id) && Objects.equals(name, regionDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
