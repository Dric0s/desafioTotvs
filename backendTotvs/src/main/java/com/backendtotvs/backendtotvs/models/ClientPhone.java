package com.backendtotvs.backendtotvs.models;

import jakarta.persistence.*;

@Entity
@Table(name = "client_phone")
public class ClientPhone {

    public ClientPhone(){}

    public ClientPhone(String phone){
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(optional=false)
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
