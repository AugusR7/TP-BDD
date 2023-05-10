package com.bddabd.tp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandAndTempOnDateDTO {
    private RegionDTO region;
    private String date;
    private Integer demand;
    private Double temperature;
}
