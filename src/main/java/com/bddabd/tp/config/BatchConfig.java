package com.bddabd.tp.config;


import com.bddabd.tp.entity.Region;
import com.bddabd.tp.service.DemandService;
import com.bddabd.tp.service.RegionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    DemandService demandService;
    RegionService regionService;

    public BatchConfig(DemandService demandService, RegionService regionService) {
        this.demandService = demandService;
        this.regionService = regionService;
    }

    private static final Logger log = Logger.getLogger(String.valueOf(BatchConfig.class));

    @Bean
    public Job importMonthlyDemandOnRegion(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("importDemandAndTemperatureOfAMonth", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("create csv", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
                    String date = jobParameters.getString("fecha");
                    String[] days = getDays(date);
                    Integer regionId = jobParameters.getLong("region").intValue();
                    PrintWriter csvWriter = new PrintWriter(new File("demands_temperatures.csv"));

                    for (String i : days) {
                        List<Integer> regionDemand = new ArrayList<>();
                        List<Double> regionTemp = new ArrayList<>();
                        List<HashMap> demandAndTemperatures = regionService.getCountryDemandOnDate("2023-01-" + i, regionId);

                        for (HashMap demandAndTemperature : demandAndTemperatures) {
                            regionDemand.add((Integer) demandAndTemperature.get("dem"));
                            regionTemp.add((Double) demandAndTemperature.get("temp"));
                        }

                        Integer demand = demandService.getTotalDemand(regionDemand);
                        Double temp = demandService.getAverageTemp(regionTemp);
                        csvWriter.println(regionId.toString() + ',' + "2023-01-" + i + ',' + demand.toString() + ',' + temp.toString());
                    }
                    csvWriter.close();
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("load csv", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    File csvFile = new File("demands_temperatures.csv");
                    Scanner csvReader = new Scanner(csvFile);
                    while (csvReader.hasNextLine()) {
                        String[] data = csvReader.nextLine().split(",");
                        Region region = regionService.getRegionById(Integer.parseInt(data[0]));
                        log.info("Se creó: " + demandService.createDemand(
                                String.valueOf(data[1]),
                                region,
                                Integer.parseInt(data[2]),
                                Double.parseDouble(data[3])
                        ));
                    }
                    csvReader.close();
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    public String[] getDays(String anioMes) {
        YearMonth yearMonth = YearMonth.parse(anioMes);
        int daysInMonth = yearMonth.lengthOfMonth();
        String[] days = new String[daysInMonth];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), day);
            String dayString = date.format(formatter);
            days[day - 1] = dayString;
        }
        return days;
    }
}


