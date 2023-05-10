package com.bddabd.tp.beans;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class InitialRegionLoadBean {

    private static final Logger LOG = Logger.getLogger(String.valueOf(InitialRegionLoadBean.class));

    @Autowired
    private RegionService regionService;

    public void initialRegionLoad() {
        LOG.info("Initial load of regions");
        List<HashMap> allRegions = regionService.getRegions();
        int size = 2;
        for (int i = 0; i < allRegions.size(); i++) {
//        for (int i = 0; i < size; i++) {
            LOG.info("Creating: " +
                    regionService.createRegion(
                            new RegionDTO(
                                    (Integer) (allRegions.get(i)).get("id"),
                                    (String) ((allRegions.get(i)).get("nombre"))
                            )
                    ).toString()
            );
        }
        LOG.info("Initial load of regions finished");
    }

}
