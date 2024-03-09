package com.example.demo.exception;

import com.example.demo.response.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception {
    private Status status;
    public CustomException(Status status) {
        this.status = status;
    }
}
