package com.backendtotvs.backendtotvs.controller;

import com.backendtotvs.backendtotvs.dto.ClientDto;
import com.backendtotvs.backendtotvs.exception.InvalidDataException;
import com.backendtotvs.backendtotvs.models.Client;
import com.backendtotvs.backendtotvs.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    /**
     *
     * @return Busca todos os clientes cadastrados
     */
    @GetMapping()
    public List<ClientDto> listAll() {
        return clientService.getAllClients();
    }


    /**
     * End-point para criar o cliente.
     * @param client
     * @return Retornar o cliete cadastrado ou o motivo pelo qual nao foi cadastrado
     */
    @PostMapping()
    public ResponseEntity<?> createClient(@RequestBody Client client) {

        try{
            ClientDto clientDto = clientService.createClient(client);
            return ResponseEntity.ok(clientDto);
        }catch (InvalidDataException invalidDataException){
            return new ResponseEntity<>(invalidDataException.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * End-point para editar o cliente.
     * @param client
     * @return Retornar o cliete editado ou o motivo pelo qual nao foi editado
     */
    @PutMapping()
    public ResponseEntity<?> editClient(@RequestBody Client client) {

        if(client.getId() == null){
            return new ResponseEntity<>(new InvalidDataException(null, Collections.singletonList("Id do cliente é obrigatório.")).getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        try{
            ClientDto clientDto = clientService.editClient(client);
            return ResponseEntity.ok(clientDto);
        }catch (InvalidDataException invalidDataException){
            return new ResponseEntity<>(invalidDataException.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

    }
}
