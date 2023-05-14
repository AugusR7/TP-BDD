package com.bddabd.tp;

import com.bddabd.tp.beans.InitialDemandOnDateLoadBean;
import com.bddabd.tp.beans.InitialRegionLoadBean;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class TpApplication {

	@Bean(initMethod = "initialRegionLoad")
	public InitialRegionLoadBean initialRegionLoadBean() {
		return new InitialRegionLoadBean();
	}

	//@Bean(initMethod = "initialDemandLoad")
	//public InitialDemandOnDateLoadBean initialDemandOnDateLoadBean() {
	//	return new InitialDemandOnDateLoadBean();
	//}

	public static void main(String[] args) {
		SpringApplication.run(TpApplication.class, args);
	}

}
