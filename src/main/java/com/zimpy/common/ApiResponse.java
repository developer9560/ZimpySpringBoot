package com.zimpy.common;

public class ApiResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.success = true;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int status, T data) {
        this.success = true;
        this.status = status;
        this.data = data;
    }

    // getters

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}

