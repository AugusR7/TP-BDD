package com.bddabd.tp.repository;

import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DemandRepository extends CrudRepository<DemandOnDate, Integer> {
    @Query("SELECT demand FROM DemandOnDate demand WHERE demand.date = ?1 AND demand.region = ?2")
    DemandOnDate findByRegionAndDate(String date, Region region);
}
