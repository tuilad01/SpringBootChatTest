package com.example.testwebapp.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {


    @Value("${config.value:unknown}")
    private String value;

    public void print() {
        System.out.println("config.value = " + this.getValue());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
