package com.bddabd.tp.beans;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

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
        List dates = getDatesOfAYear("2022");
        LOG.info("Initial load of demand on date of regions");

//        for (HashMap oneRegion : allRegions) {
        for (int i = 0; i < 2; i++) {
            HashMap oneRegion = allRegions.get(i);
            Region region = regionService.getRegionById((Integer) oneRegion.get("id"));
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
//                );
            }
        }
        LOG.info("Initial load of demand on dates finished");
    }

    public List<String> getDatesOfAYear(String year) {
        List<String> dates = new ArrayList<>();
        String[] days = getDays();
        String[] months = getMonths();

        for (int i = 0; i < months.length; i++) {
            if (i == 1) {
                for (int e = 0; e < 28; e++) {
                    dates.add(year + '-' + months[i] + '-' + days[e]);
                }
            } else {
                for (int z = 0; z < 30; z++) {
                    dates.add(year + '-' + months[i] + '-' + days[z]);
                }
                if (i != 3 && i != 5 && i != 8 && i != 10) {
                    dates.add(year + '-' + months[i] + '-' + days[30]);
                }
            }
        }
        return dates;
    }

    public String[] getDays() {
        String[] days = new String[31];
        for (int d = 1; d < 32; d++) {
            String day;
            if (d < 10) {
                day = "0" + Integer.toString(d);
            } else {
                day = Integer.toString(d);
            }
            days[d - 1] = day;
        }
        LOG.info("Days: " + Arrays.toString(days));
        return days;
    }

    public String[] getMonths() {
        int size = 12;
        String[] months = new String[size];
//        for (int m = 1; m < 13; m++) {
        for (int m = 1; m < size + 1; m++) {
            String month;
            if (m < 10) {
                month = "0" + Integer.toString(m);
            } else {
                month = Integer.toString(m);
            }
            months[m - 1] = month;
        }
        LOG.info("Months: " + Arrays.toString(months));
        return months;
    }
}
