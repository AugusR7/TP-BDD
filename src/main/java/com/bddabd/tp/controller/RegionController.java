package com.bddabd.tp.controller;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.CammesaService;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.web.bind.annotation.*;

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

        List<HashMap> countryDemand = regionService.getCountryDemandOnDate(feriadoMasCercanoALaFecha, 1002);

        Region region = regionService.getRegionById(1002);
        System.out.println(region);

        for (int i = 0; i < countryDemand.size(); i++) {
            demandService.createDemand(
                    new DemandOnDateDTO(
                        region,
                        feriadoMasCercanoALaFecha,
                        (Integer) countryDemand.get(i).get("dem")
                    )
            );
        }

        Integer demanda = 0;
        for (int i = 0; i < countryDemand.size(); i++) {
            demanda += (Integer) countryDemand.get(i).get("dem");
        }

//        String mensaje = "La demanda promedio del país en lá fecha \"" + feriadoMasCercanoALaFecha + "\" fue de: " + demanda / countryDemand.size() + " MW";

        return new DemandOnDateDTO(region, feriadoMasCercanoALaFecha, demanda / countryDemand.size());
//        return "Hola";
    }
}
