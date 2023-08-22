package com.backendtotvs.backendtotvs.repository;

import com.backendtotvs.backendtotvs.models.ClientPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientPhoneRepository extends JpaRepository<ClientPhone, Long> {
    ClientPhone findByPhone(String phone);

    List<ClientPhone> findAllByClientId(Long clientId);
}
