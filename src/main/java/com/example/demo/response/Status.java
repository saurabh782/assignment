package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {
    private Integer statusCode;
    private String type;
    private String statusMessage;

    public Status(Integer statusCode, String statusMessage, String type) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.type = type;
    }
}
