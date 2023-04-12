package com.example.demo.service;

import com.example.demo.exceptions.DateException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Boolean.parseBoolean;

@Service
public class CammesaService {

    RestTemplateBuilder restTemplateBuilder;

    public CammesaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public String healthCheck() {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/ping", String.class);
    }

    public String esFeriado(String fecha) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/EsDiaFeriado?fecha=" + fecha,
                        String.class);
    }

    public String feriadoMasCercano(String fecha) throws DateException{ //Retorna el feriado mas cercano
        //YYYY-MM-DD
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE);
        if(date.isAfter(today)){
            throw new DateException("La fecha ingresada es mayor a la fecha actual");
        }
        int i = 1;
        boolean res = parseBoolean(esFeriado(fecha));
        System.out.println(res);
        while (res) {
            LocalDate dayAfter = date.plusDays(i);
            res = parseBoolean(esFeriado(dayAfter.toString()));
            if (res){
                return dayAfter.toString();
            }

            LocalDate dayBefore = date.minusDays(i);
            res = parseBoolean(esFeriado(dayBefore.toString()));
            if (res){
                return dayBefore.toString();
            }
            i++;
        }
        return "Llegó al final";
    }
}
