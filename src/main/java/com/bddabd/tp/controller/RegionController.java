package com.bddabd.tp.controller;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.CammesaService;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;
    private final CammesaService cammesaService;
    private final DemandService demandService;


    @GetMapping("/getRegionsByName")
    public List<HashMap> getRegionsByName() {
        List<HashMap> allRegions = regionService.getRegions();
        for (HashMap allRegion : allRegions) {
            regionService.createRegion(
                    new RegionDTO(
                            (Integer) allRegion.get("id"),
                            (String) (allRegion.get("nombre"))
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
    public DemandOnDateDTO demandaFeriadoMasCercano(@RequestParam(value = "fecha") String fecha) {
        String feriadoMasCercanoALaFecha = cammesaService.feriadoMasCercano(fecha);
        Region region = regionService.getRegionById(1002);
        if (demandService.getRegionDemandOnDate(feriadoMasCercanoALaFecha, region) != null) {
            DemandOnDate regionDemandOnDate = demandService.getRegionDemandOnDate(feriadoMasCercanoALaFecha, region);
            RegionDTO regionDTO = new RegionDTO(regionDemandOnDate.getRegion().getId(), regionDemandOnDate.getRegion().getName());
            return new DemandOnDateDTO(regionDTO, regionDemandOnDate.getDate(), regionDemandOnDate.getDemand() / 289);
        } else {
            List<HashMap> countryDemand = regionService.getCountryDemandOnDate(feriadoMasCercanoALaFecha, 1002);
            List<Integer> regionDemand = new ArrayList<Integer>();
            Double averageTemp = 0.0;

            for (HashMap demand : countryDemand) {
                regionDemand.add((Integer) demand.get("dem"));
            }
            Integer demand = demandService.getTotalDemand(regionDemand);
            demandService.createDemand(feriadoMasCercanoALaFecha, region, demand, 0.0);
            DemandOnDate regionDemandOnDate = demandService.getRegionDemandOnDate(feriadoMasCercanoALaFecha, region);
            RegionDTO regionDTO = new RegionDTO(regionDemandOnDate.getRegion().getId(), regionDemandOnDate.getRegion().getName());
            return new DemandOnDateDTO(regionDTO, regionDemandOnDate.getDate(), demandService.getTotalDemand(regionDemand) / 289);
        }

    }


    @PostMapping("/actualizarRegiones")
    public String actualizarRegiones() {
        List<HashMap> allRegions = regionService.getRegions();
        for (HashMap region : allRegions) {
            regionService.createRegion(
                    new RegionDTO(
                            (Integer) region.get("id"),
                            (String) (region.get("nombre"))
                    )
            );
        }
        return "Regiones actualizadas";
    }


}