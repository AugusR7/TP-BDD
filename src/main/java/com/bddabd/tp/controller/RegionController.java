package com.bddabd.tp.controller;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.CammesaService;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RegionController {

    private RegionService regionService;
    private CammesaService cammesaService;

    private DemandService demandService;

    RegionController(RegionService regionService, CammesaService cammesaService, DemandService demandService) {
        this.regionService = regionService;
        this.cammesaService = cammesaService;
        this.demandService = demandService;
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
    public DemandOnDateDTO demandaFeriadoMasCercano(@RequestParam(value = "fecha") String fecha) {
        String feriadoMasCercanoALaFecha = cammesaService.feriadoMasCercano(fecha);
        Region region = regionService.getRegionById(1002);
        if (demandService.getRegionDemandOnDate(feriadoMasCercanoALaFecha, region) != null) {
            DemandOnDate regionDemandOnDate = demandService.getRegionDemandOnDate(feriadoMasCercanoALaFecha, region);
            return new DemandOnDateDTO(regionDemandOnDate.getRegion(), regionDemandOnDate.getDate(), regionDemandOnDate.getDemand() / 289,0.0);
        } else {
            List<HashMap> countryDemand = regionService.getCountryDemandOnDate(feriadoMasCercanoALaFecha, 1002);

            List<Integer> regionDemand = new ArrayList<Integer>();

            for (HashMap hashMap : countryDemand) {
                regionDemand.add((Integer) hashMap.get("dem"));
            }
            Integer demand = demandService.getTotalDemand(regionDemand);
            demandService.createDemand(feriadoMasCercanoALaFecha, region, demand, 0.0);
            DemandOnDate regionDemandOnDate = demandService.getRegionDemandOnDate(feriadoMasCercanoALaFecha, region);
            return new DemandOnDateDTO(regionDemandOnDate.getRegion(), regionDemandOnDate.getDate(), demandService.getTotalDemand(regionDemand) / 289,0.0);
        }

    }


    @PostMapping("/actualizarRegiones")
    public String actualizarRegiones() {
        List<HashMap> allRegions = regionService.getRegions();
        for (int i = 0; i < allRegions.size(); i++) {
            regionService.createRegion(
                    new RegionDTO(
                            (Integer) (allRegions.get(i)).get("id"),
                            (String) ((allRegions.get(i)).get("nombre"))
                    )
            );
        }
        return "Regiones actualizadas";
    }



}