package com.backendtotvs.backendtotvs.dto;

import java.util.List;

public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private String neighborhood;

    private List<ClientPhoneDto> phones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public List<ClientPhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<ClientPhoneDto> phones) {
        this.phones = phones;
    }
}
