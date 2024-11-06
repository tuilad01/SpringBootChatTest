package com.example.testwebapp.common;

import org.springframework.stereotype.Component;

@Component
public class RequestHeaderObject {
    public RequestHeaderObject() {

    }

    public String clientIPAddress = "";
    public String data = "";
}
