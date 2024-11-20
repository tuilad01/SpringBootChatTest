package com.example.testwebapp.common;

public class ResponseObject<T> {
    private boolean ok;
    private String message;
    private String error;
    private T data;

    public ResponseObject() {
    }

    public ResponseObject(boolean ok, T data) {
        this.ok = ok;
        this.data = data;
    }

    public ResponseObject(boolean ok, String message, String error, T data) {
        this.ok = ok;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
