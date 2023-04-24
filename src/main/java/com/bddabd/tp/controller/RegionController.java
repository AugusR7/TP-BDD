package com.bddabd.tp.controller;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.service.CammesaService;
import com.bddabd.tp.service.RegionService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import com.bddabd.tp.service.CammesaService;

@RestController
@RequestMapping("/api/v1")
public class RegionController {

    private RegionService regionService;
    private CammesaService cammesaService;

    RegionController(RegionService regionService, CammesaService cammesaService) {
        this.regionService = regionService;
        this.cammesaService = cammesaService;
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
        String feriadoMasCercanoALaFecha = cammesaService.feriadoMasCercano(fecha);
        System.out.println(feriadoMasCercanoALaFecha);
        List<HashMap> countryDemand = regionService.getCountryDemandOnDate(feriadoMasCercanoALaFecha, 1002);
        Integer demanda = 0;
        for( int i = 0; i < countryDemand.size(); i++){
            demanda += (Integer) countryDemand.get(i).get("dem");
        }
        String mensaje = "La demanda promedio del país en lá fecha \"" + feriadoMasCercanoALaFecha +
                "\" fue de: " + demanda/countryDemand.size() + " MW";
        return mensaje;
    }
}
