package com.backendtotvs.backendtotvs.repository;

import com.backendtotvs.backendtotvs.models.Client;
import com.backendtotvs.backendtotvs.models.ClientPhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByName(String name);

}
