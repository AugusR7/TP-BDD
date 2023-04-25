package com.bddabd.tp.dto;

import com.bddabd.tp.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandOnDateDTO {
    private Region region;
    private String date;
    private Integer demand;
}
