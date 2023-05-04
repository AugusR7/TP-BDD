package com.bddabd.tp.service;

import com.bddabd.tp.dto.RegionDTO;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;
    RestTemplateBuilder restTemplateBuilder;

    public RegionService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List getRegions() {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/RegionesDemanda",
                        List.class);
    }

//    public List

    public Region createRegion(RegionDTO regionDTO) {
        return regionRepository.save(new Region(regionDTO.getId(), regionDTO.getName()));
    }

    public String deleteRegion(Integer id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
            return "Region con id " + id + " eliminada";
        } else {
            return "Region con id " + id + " no existe";
        }
    }

    public List getCountryDemandOnDate(String fecha, Integer id) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha="
                                + fecha + "&id_region=" + id,
                        List.class);
    }

    public Region getRegionById(Integer id) {
        if (regionRepository.findById(id).isPresent())
            return regionRepository.findById(id).get();
        else return null;
    }
}
