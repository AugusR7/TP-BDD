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

    public void createRegion(RegionDTO regionDTO) {
        regionRepository.save(new Region(regionDTO.getId(), regionDTO.getName()));
    }

}
