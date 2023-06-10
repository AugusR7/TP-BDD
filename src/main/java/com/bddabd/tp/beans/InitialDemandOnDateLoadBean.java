package com.bddabd.tp.beans;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class InitialDemandOnDateLoadBean {

    private static final Logger LOG = Logger.getLogger(String.valueOf(InitialDemandOnDateLoadBean.class));

    @Autowired
    private DemandService demandService;

    @Autowired
    private RegionService regionService;

    public void initialDemandLoad() {
        List<HashMap> allRegions = regionService.getRegions();
        String[] dates = generateYearDates(2022);
        LOG.info("Initial load of demand on date of regions");

        for (HashMap oneRegion : allRegions) {
            Region region = regionService.getRegionById((Integer) oneRegion.get("id"));
            LOG.info("Loading for region: " + region.getName() + " with id: " + region.getId() + " started");
            for (Object date : dates) {
                List<HashMap> demandAndTempOnDate = demandService.demandaYTemperaturaEnFecha((String) date, (Integer) region.getId());
                List regionDemand = new ArrayList<Integer>();
                List regionTemp = new ArrayList<Double>();
                for (HashMap hashMap : demandAndTempOnDate) {
                    regionDemand.add(hashMap.get("dem"));
                    if (hashMap.get("temp") != null) regionTemp.add(hashMap.get("temp"));
                }
                Integer demand = demandService.getTotalDemand(regionDemand);
                Double temp = demandService.getAverageTemp(regionTemp);

                demandService.createDemand(
                        (String) date,
                        region,
                        demand,
                        temp
                ).toString();
            }
        }
        LOG.info("Initial load of demand on dates finished");
    }

    public String[] generateYearDates(int year) {
//        int daysInYear = LocalDate.ofYearDay(year, 365).getDayOfYear();
        int daysInYear = 31;
        String[] dates = new String[daysInYear];

        LocalDate date = LocalDate.ofYearDay(year, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < daysInYear; i++) {
            dates[i] = date.format(formatter);
            date = date.plusDays(1);
        }

        return dates;
    }
}
