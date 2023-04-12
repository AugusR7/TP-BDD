package com.example.demo.dto;

import lombok.Data;

@Data
public class HelloWorldResponse {
    private String text;

    public HelloWorldResponse(String text){
        this.text = text;

    }
}
