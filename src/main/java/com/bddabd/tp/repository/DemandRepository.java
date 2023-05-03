package com.bddabd.tp.repository;

import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DemandRepository extends CrudRepository<DemandOnDate, Integer> {
    @Query("SELECT demand FROM DemandOnDate demand WHERE demand.date = ?1 AND demand.region = ?2")
    DemandOnDate findByRegionAndDate(String date, Region region);

    @Query("SELECT MAX(demand), date, region FROM DemandOnDate GROUP BY region")
    List<Integer> maxDemandDatePerRegion();
}
