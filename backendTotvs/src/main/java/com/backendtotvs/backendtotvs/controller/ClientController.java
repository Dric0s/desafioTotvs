package com.backendtotvs.backendtotvs.controller;

import com.backendtotvs.backendtotvs.dto.ClientDto;
import com.backendtotvs.backendtotvs.exception.InvalidDataException;
import com.backendtotvs.backendtotvs.models.Client;
import com.backendtotvs.backendtotvs.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping()
    public List<ClientDto> listAll() {
        return clientService.getAllClients();
    }

    @PostMapping()
    public ResponseEntity<?> createClient(@RequestBody Client client) {

        try{
            ClientDto clientDto = clientService.createClient(client);
            return ResponseEntity.ok(clientDto);
        }catch (InvalidDataException invalidDataException){
            return new ResponseEntity<>(invalidDataException.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

    }
}
