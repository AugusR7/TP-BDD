package com.bddabd.tp.beans;

import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InitialDemandOnDateLoadBean {

    private static final Logger LOG = Logger.getLogger(String.valueOf(InitialDemandOnDateLoadBean.class));

    @Autowired
    private DemandService demandService;

    @Autowired
    private RegionService regionService;

//    public void initialDemandLoad() {
//        LOG.info("Initial load of demand on date of regions");
//        List allRegions = regionService.getRegions();
//        List dates = getDatesOfAYear("2022");
//        for (int i = 0; i < allRegions.size(); i++) {
//            for (int e = 0; i < dates.size(); e++) {
//                DemandOnDate demand = demandService.getRegionDemandOnDate((String) dates.get(e), (Region) allRegions.get(i));
//                LOG.info("Creating: " +
//                        demandService.createDemand(
//                                (String) (dates.get(e)),
//                                (Region) (allRegions.get(i)),
//                                demand.getDemand()
//                        ).toString()
//                );
//            }
//        }
//        LOG.info("Initial load of demand on dates finished");
//    }

    public List<String> getDatesOfAYear(String year) {
        List<String> dates = new ArrayList<>();
        String[] days = getDays();
        String[] months = getMonths();

        for (int i = 0; i < 12; i++) {
            if (i == 1 ) {
                for (int e = 0; e < 28; e++) {
                    dates.add(year + '-' + months[i] + '-' + days[e]);
                }
            }
            else {
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
        for (int d = 0; d < 30; d++) {
            String day;
            if (d < 10) {
                day = "0" + Integer.toString(d);
            }
            else {
                day = Integer.toString(d);
            }
            days[d] = day;
        }

        return days;
    }

    public String[] getMonths() {
        String[] months = new String[12];
        for (int m = 0; m < 12; m++) {
            String month;
            if (m < 10) {
                month = "0" + Integer.toString(m);
            } else {
                month = Integer.toString(m);
            }
            months[m] = month;
        }

        return months;
    }
}
