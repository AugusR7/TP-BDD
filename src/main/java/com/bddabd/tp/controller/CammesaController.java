package com.bddabd.tp.controller;

import com.bddabd.tp.service.CammesaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CammesaController {
    private CammesaService cammesaService;

    CammesaController(CammesaService cammesaService) {
        this.cammesaService = cammesaService;
    }

    @GetMapping("/health")
    public String HealthResponse() {
        return "ok";
    }

    @PostMapping("/demandaFeriadoMasCercano")
    public String demandaFeriadoMasCercano(@RequestParam(value = "fecha", defaultValue = "2023-01-01") String fecha) {
        return cammesaService.feriadoMasCercano(fecha);
    }

    @GetMapping("/cammesa")
    public String cammesa(@RequestParam(value = "fecha", defaultValue = "2023-01-01") String fecha) {
        return cammesaService.esFeriado(fecha);
    }
}
