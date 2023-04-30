package com.bddabd.tp.service;

import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService {

    @Autowired
    DemandRepository demandRepository;

    RestTemplateBuilder restTemplateBuilder;

    public DemandService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public DemandOnDate createDemand(String date, Region region, List<Integer> demand) {
        return demandRepository.save(new DemandOnDate(date, region, demand));
    }

    public DemandOnDate getRegionDemandOnDate(String date, Region region) {
        return demandRepository.findByRegionAndDate(date, region);
    }

    public List<DemandOnDateDTO> maxDemandDatePerRegion() {
        return demandRepository.maxDemandDatePerRegion();
    }
}
