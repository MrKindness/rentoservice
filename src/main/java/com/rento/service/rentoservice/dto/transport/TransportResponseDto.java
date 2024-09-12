package com.rento.service.rentoservice.dto.transport;

import com.rento.service.rentoservice.entity.transport.Transport;

public class TransportResponseDto {
    private String brand;
    private String model;
    private String year;
    private String location;
    private String address;
    private String status;
    private String description;

    public TransportResponseDto(Transport transport) {
        this.brand = transport.getBrand();
        this.model = transport.getModel();
        this.year = transport.getYear().toString();
        this.location = transport.getLocation();
        this.address = transport.getAddress();
        this.status = transport.getStatus().name();
        this.description = transport.getDescription();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
