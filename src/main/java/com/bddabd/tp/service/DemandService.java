package com.bddabd.tp.service;

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

    public DemandOnDate createDemand(String date, Region region, Integer demand, Double temperature) {
        return demandRepository.save(new DemandOnDate(date, region, demand, temperature));
    }

    public DemandOnDate getRegionDemandOnDate(String date, Region region) {
        return demandRepository.findByRegionAndDate(date, region);
    }

    public List<Object[]> maxDemandDatePerRegion() {
        List<Object[]> response = demandRepository.maxDemandDatePerRegion();
        System.out.println(response.get(0)[0]);
        return response;
    }

    public List demandaYTemperaturaEnFecha(String date, Integer region) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha="
                                + date + "&id_region=" + region,
                        List.class);
    }

    public Integer getTotalDemand(List<Integer> demand) {
        Integer sum = 0;
        for (Integer i : demand) {
            sum += i;
        }
        return sum;
    }

    public Double getAverageTemp(List<Double> regionTemp) {
        Double sum = 0.0;
        for (Double temp : regionTemp) {
            sum += temp;
        }

        return sum / regionTemp.size();
    }
}
