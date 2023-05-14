package com.bddabd.tp.config;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger log = Logger.getLogger(String.valueOf(JobCompletionNotificationListener.class));
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().toString().equals("COMPLETED")) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            jdbcTemplate.query("SELECT region_id, fecha, demanda, temperatura FROM demandOnDate",
                    (rs, row) -> "Region: " + rs.getString(1) + " Fecha: " + rs.getString(2) + " Demanda: " + rs.getString(3)
            ).forEach(log::info);
//            ).forEach(str -> log.info(str));
        }
    }

}

