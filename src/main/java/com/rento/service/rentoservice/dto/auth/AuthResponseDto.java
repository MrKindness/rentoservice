package com.rento.service.rentoservice.dto.auth;

public class AuthResponseDto {
    private String value;
    private boolean result;

    public AuthResponseDto(String token, boolean result) {
        this.value = token;
        this.result = result;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
