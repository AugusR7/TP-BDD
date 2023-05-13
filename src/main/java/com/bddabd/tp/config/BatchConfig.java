package com.bddabd.tp.config;

import com.bddabd.tp.entity.DemandOnDate;
import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Configuration
public class BatchConfig {

    @Autowired
    DemandService demandService;

    @Autowired
    RegionService regionService;

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importDemandAndTemperatureOfAMonth", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("create csv", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    String[] days = getDays();
                    Integer regionId = 1002;
                    PrintWriter csvWriter = new PrintWriter(new File("demands_temperatures.csv"));

                    for (String i: days) {
                        List<Integer> regionDemand = new ArrayList<>();
                        List<Double> regionTemp = new ArrayList<>();
                        List<HashMap> demandAndTemperature = regionService.getCountryDemandOnDate("2023-01-" + i, regionId);

                        for (HashMap hashMap : demandAndTemperature) {
                            regionDemand.add((Integer) hashMap.get("dem"));
                            regionTemp.add((Double) hashMap.get("temp"));
                        }

                        Integer demand = demandService.getTotalDemand(regionDemand);
                        Double temp = demandService.getAverageTemp(regionTemp);
                        csvWriter.println(regionId.toString() + ',' + "2023-01-" + i.toString() + ',' + demand.toString() + ',' + temp.toString());
                    }
                    csvWriter.close();
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    public String[] getDays() {
        String[] days = new String[31];
        for (int d = 1; d < 32; d++) {
            String day;
            if (d < 10) {
                day = "0" + Integer.toString(d);
            } else {
                day = Integer.toString(d);
            }
            days[d - 1] = day;
        }
        return days;
    }
}


