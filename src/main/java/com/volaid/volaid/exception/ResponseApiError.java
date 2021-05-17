package com.volaid.volaid.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ResponseApiError extends RuntimeException {

    private int code;
    private String message;

    public ResponseApiError() {
        super();
    }

    public ResponseApiError(String message) {
        super(message);
        this.message = message;
    }

    public ResponseApiError(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResponseApiError(int status, int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseApiError{" +
                ", code=" + code +
                ", message=" + message +
                '}';
    }
}
