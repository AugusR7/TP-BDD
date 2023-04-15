package com.bddabd.tp.controller;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.service.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RegionController {

    private RegionService regionService;

    RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/getRegionsByName")
    public List<HashMap> getRegionsByName() {
        List<HashMap> allRegions = regionService.getRegions();
        for (int i = 0; i < allRegions.size(); i++) {
            regionService.createRegion(new RegionDTO((Integer) (allRegions.get(i)).get("id"),
                    (String) ((allRegions.get(i)).get("nombre"))));
        }
        return allRegions;
    }

}
