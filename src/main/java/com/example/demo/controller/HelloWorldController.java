package com.example.demo.controller;

import com.example.demo.dto.HelloWorldResponse;
import com.example.demo.service.CammesaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class HelloWorldController {
    private CammesaService cammesaService;

    HelloWorldController(CammesaService cammesaService) {
        this.cammesaService = cammesaService;
    }

    @GetMapping("/health")
    public String HealthResponse() {
        return cammesaService.healthCheck();
    }

    @PostMapping("/demandaFeriadoMasCercano")
    public String demandaFeriadoMasCercano(@RequestParam(value = "fecha", defaultValue = "2023-01-01") String fecha) {
        return cammesaService.feriadoMasCercano(fecha);
    }

    @GetMapping("/hello")
    public HelloWorldResponse helloWorld(@RequestParam(value = "name", defaultValue = "world") String name) {
        return new HelloWorldResponse("Hello " + name + "!");
    }

    @GetMapping("/helloWorld")
    public String helloWorldAlone() {
        return "Hello!";
    }

    @GetMapping("/cammesa")
    public String cammesa(@RequestParam(value = "fecha", defaultValue = "2023-01-01") String fecha) {
        return cammesaService.esFeriado(fecha);
    }
}
