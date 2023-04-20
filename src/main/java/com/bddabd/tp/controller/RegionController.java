package com.bddabd.tp.controller;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.service.RegionService;
import org.springframework.web.bind.annotation.*;

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
            regionService.createRegion(
                    new RegionDTO(
                            (Integer) (allRegions.get(i)).get("id"),
                            (String) ((allRegions.get(i)).get("nombre"))
                    )
            );
        }
        return allRegions;
    }

    @DeleteMapping("/deleteRegion")
    public String deleteRegion(@RequestParam(value = "id") Integer id) {
        return regionService.deleteRegion(id);
    }

    @GetMapping("/demandaFeriadoMasCercano")
    public String demandaFeriadoMasCercano(@RequestParam(value = "fecha") String fecha) {
        List<HashMap> countryDemand = regionService.getCountryDemandOnDate(fecha, 1002);
        System.out.println(countryDemand);
        return "Hola";
    }
}
