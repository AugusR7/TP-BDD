package com.bddabd.tp.repository;

import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DemandRepository extends CrudRepository<DemandOnDate, Integer> {
    @Query("SELECT demand FROM DemandOnDate demand WHERE demand.date = ?1 AND demand.region = ?2")
    DemandOnDate findByRegionAndDate(String date, Region region);

    @Query(value = "SELECT DISTINCT ON(d.country_region_id) d.id, d.country_region_id, d.date, d.demand, d.temperature FROM demand_on_date d INNER JOIN (SELECT country_region_id, MAX(demand) AS max_demand FROM demand_on_date GROUP BY country_region_id) md ON d.country_region_id = md.country_region_id AND d.demand = md.max_demand ORDER BY d.country_region_id", nativeQuery = true)
    List<Object[]> maxDemandDatePerRegion();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM demand_on_date WHERE country_region_id = :id", nativeQuery = true)
    int deleteByRegion(@Param("id") Integer id);
}
