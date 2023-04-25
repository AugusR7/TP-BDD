package com.bddabd.tp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "demand_on_date")
//@AllArgsConstructor
@NoArgsConstructor
public @Data class DemandOnDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demand_on_date_id")
    @Getter
    private Integer id;

    @Column(name = "demand_on_date_date")
    @Getter
    private String date;

    // Lazy porque cuando queremos ver la demanda, se hará con varias demandas a la vez, y no necesariamente vamos a traer a la region (?)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_region_id")
    @Getter
    private Region region;


    @Column(name = "demand_on_date_demand")
    @Getter
    private Integer demand;

    public DemandOnDate(String date, Region region, Integer demand) {
        this.date = date;
        this.region = region;
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "DemandOnDate{"
                + "id=" + this.id
                + ", date='" + this.date
                + ", demand='" + this.demand
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DemandOnDate))
            return false;
        DemandOnDate demandOnDate = (DemandOnDate) o;
        return Objects.equals(this.id, demandOnDate.id) && Objects.equals(this.date, demandOnDate.date) && Objects.equals(this.demand, demandOnDate.demand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.date, this.demand);
    }

}
