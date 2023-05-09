package com.bddabd.tp.controller;

import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.dto.RegionDTO;
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
public class DemandController {

    private RegionService regionService;
    private CammesaService cammesaService;

    private DemandService demandService;

    DemandController(RegionService regionService, CammesaService cammesaService, DemandService demandService) {
        this.regionService = regionService;
        this.cammesaService = cammesaService;
        this.demandService = demandService;
    }

    @PostMapping("/demandaYTemperaturaDiario")
    public DemandOnDateDTO demandaYTemperaturaEnFecha(@RequestParam(value = "fecha") String fecha, @RequestParam(value = "region") Integer region) {
        List<HashMap> demandAndTempOnDate = demandService.demandaYTemperaturaEnFecha(fecha, region);
        Region region1 = regionService.getRegionById(region);
        System.out.println(demandAndTempOnDate);
        List regionDemand = new ArrayList<Integer>();
        List regionTemp = new ArrayList<Double>();

        for (HashMap hashMap : demandAndTempOnDate) {

//            System.out.println(hashMap.get("dem"));
//            System.out.println(hashMap.get("temp"));

            regionDemand.add(hashMap.get("dem"));
            if (hashMap.get("temp") != null) regionTemp.add(hashMap.get("temp"));
        }

        Integer demand = demandService.getTotalDemand(regionDemand);
        Double temp = demandService.getAverageTemp(regionTemp);

        demandService.createDemand(fecha, region1, demand, temp);

        return new DemandOnDateDTO(region1, fecha, demand, temp);
    }

    @GetMapping("/diaConMayorDemanda")
    public List<Object[]> diaConMayorDemanda() {
        return demandService.maxDemandDatePerRegion();
    }
}
