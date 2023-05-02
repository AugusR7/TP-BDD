package com.bddabd.tp.dto;

import com.bddabd.tp.entity.Region;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandOnDateDTO {
    private Region region;
    private String date;
    private Integer demand;
}
