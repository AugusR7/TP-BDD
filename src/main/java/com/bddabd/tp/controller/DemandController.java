package com.bddabd.tp.controller;

import com.bddabd.tp.dto.DemandAndTempOnDateDTO;
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
    public DemandAndTempOnDateDTO demandaYTemperaturaEnFecha(@RequestParam(value = "fecha") String fecha, @RequestParam(value = "region") Integer region) {
        List<HashMap> demandAndTempOnDate = demandService.demandaYTemperaturaEnFecha(fecha, region);
        Region regionEnCuestion = regionService.getRegionById(region);
//        System.out.println(demandAndTempOnDate);
        if (regionEnCuestion != null) {

            List regionDemand = new ArrayList<Integer>();
            List regionTemp = new ArrayList<Double>();

            for (HashMap hashMap : demandAndTempOnDate) {
                regionDemand.add(hashMap.get("dem"));
                if (hashMap.get("temp") != null) regionTemp.add(hashMap.get("temp"));
            }

            Integer demand = demandService.getTotalDemand(regionDemand);
            Double temp = demandService.getAverageTemp(regionTemp);

            demandService.createDemand(fecha, regionEnCuestion, demand, temp);
            return new DemandAndTempOnDateDTO(new RegionDTO(regionEnCuestion.getId(), regionEnCuestion.getName()), fecha, demand, temp);
        } else {
            return null;
        }
    }

    @GetMapping("/diaConMayorDemanda")
    public List<DemandAndTempOnDateDTO> diaConMayorDemanda() {
        List<DemandOnDate> demandOnDates = demandService.maxDemandDatePerRegion();
        System.out.println(demandOnDates);
        List<DemandAndTempOnDateDTO> demandOnDateDTOS = new ArrayList<DemandAndTempOnDateDTO>();
        for (DemandOnDate demandOnDate : demandOnDates) {
            demandOnDateDTOS.add(new DemandAndTempOnDateDTO(
                    new RegionDTO(demandOnDate.getRegion().getId(), demandOnDate.getRegion().getName()),
                    demandOnDate.getDate(),
                    demandOnDate.getDemand(),
                    demandOnDate.getTemperature()));
        }
//        return demandService.maxDemandDatePerRegion();
        return demandOnDateDTOS;
    }
}
