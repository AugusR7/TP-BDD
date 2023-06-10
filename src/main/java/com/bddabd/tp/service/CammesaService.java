package com.bddabd.tp.service;

import com.bddabd.tp.exceptions.DateException;
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

    public String esFeriado(String fecha) {
        return restTemplateBuilder
                .build()
                .getForObject("https://api.cammesa.com/demanda-svc/demanda/EsDiaFeriado?fecha=" + fecha,
                        String.class);
    }

    public String feriadoMasCercano(String fecha) throws DateException {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate feriadoMasCercano = LocalDate.parse(fecha);

        if (date.isAfter(today)) throw new DateException("La fecha ingresada es mayor a la fecha actual");
        boolean esFeriado = parseBoolean(esFeriado(fecha));

        int i = 1;
        while (!esFeriado) {
            LocalDate dayAfter = date.plusDays(i);
            esFeriado = parseBoolean(esFeriado(dayAfter.toString()));
            if (esFeriado) {
                feriadoMasCercano = dayAfter;
            } else {
                LocalDate dayBefore = date.minusDays(i);
                esFeriado = parseBoolean(esFeriado(dayBefore.toString()));
                if (esFeriado) feriadoMasCercano = dayBefore;
            }
            i++;
        }
        return feriadoMasCercano.toString();
    }
}
