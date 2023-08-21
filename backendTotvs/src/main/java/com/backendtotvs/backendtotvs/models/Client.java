package com.backendtotvs.backendtotvs.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    public Client(){

    }

    public Client( String name, String address, String neighborhood, List<ClientPhone> phones) {
        this.name = name;
        this.address = address;
        this.neighborhood = neighborhood;
        this.phones = phones;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "neighborhood")
    private String neighborhood;

    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="client")
    private List<ClientPhone> phones;

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

    public List<ClientPhone> getPhones() {
        return phones;
    }

    public void setPhones(List<ClientPhone> phones) {
        this.phones = phones;
    }
}
