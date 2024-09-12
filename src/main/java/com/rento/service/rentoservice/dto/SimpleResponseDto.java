package com.rento.service.rentoservice.dto;

public class SimpleResponseDto {
    private boolean success;
    private String value;

    public SimpleResponseDto(boolean success, String value) {
        this.success = success;
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
