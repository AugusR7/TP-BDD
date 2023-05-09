package com.bddabd.tp.service;

import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandService {

    @Autowired
    DemandRepository demandRepository;

    @Autowired
            RegionService regionService;

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

    public List<DemandOnDate> maxDemandDatePerRegion() {
        List<Object[]> response = demandRepository.maxDemandDatePerRegion();
        List demandsOnDate = new ArrayList();
        for (Object[] res: response ) {
            Region region = regionService.getRegionById((Integer) res[1]);
            demandsOnDate.add(new DemandOnDate((String) res[2], region, (Integer) res[3], (Double) res[4]));
        }
        return demandsOnDate;
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
