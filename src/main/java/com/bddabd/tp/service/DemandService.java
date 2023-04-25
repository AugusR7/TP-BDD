package com.bddabd.tp.service;

import com.bddabd.tp.dto.DemandOnDateDTO;
import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

@Service
public class DemandService {

    @Autowired
    DemandRepository demandRepository;

    RestTemplateBuilder restTemplateBuilder;

    public DemandService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public void createDemand(DemandOnDateDTO demandOnDateDTO) {
//        System.out.println(
                demandRepository.save(new
                        DemandOnDate(
                            demandOnDateDTO.getDate(),
                            demandOnDateDTO.getRegion(),
                            demandOnDateDTO.getDemand()
                        )
                );
//        );
    }
}
