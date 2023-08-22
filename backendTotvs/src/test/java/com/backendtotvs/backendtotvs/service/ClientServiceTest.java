package com.backendtotvs.backendtotvs.service;

import com.backendtotvs.backendtotvs.exception.ErrorMessage;
import com.backendtotvs.backendtotvs.exception.InvalidDataException;
import com.backendtotvs.backendtotvs.models.Client;
import com.backendtotvs.backendtotvs.models.ClientPhone;
import com.backendtotvs.backendtotvs.repository.ClientPhoneRepository;
import com.backendtotvs.backendtotvs.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ClientPhoneRepository clientPhoneRepository;

    @Mock
    ModelMapper modelMapper;
    Client client;

    @BeforeEach
    public void setUp(){

        List<ClientPhone> phones = new ArrayList<>();
        phones.add(new ClientPhone("61991132903"));
        phones.add(new ClientPhone("61991132102"));


        client = new Client("Rodrigo de Souza Lala", "Rua 2", "Jardins", phones);
    }

    @Test
    void validatePhoneIsNotNull(){
        List<String> listErrors = new ArrayList<>();

        List<ClientPhone> phones = new ArrayList<>();
        phones.add(new ClientPhone(""));
        phones.add(new ClientPhone(null));

        client.setPhones(phones);

        clientService.validatePhones(client, listErrors);

        assertEquals(2, listErrors.size(), "A lista deve conter 2 erros");

        assertTrue(listErrors.contains("O 1º telefone está nulo ou vazio."));
        assertTrue(listErrors.contains("O 2º telefone está nulo ou vazio."));
    }

    @Test
    void validateClientPhoneIsAlreadyAdd(){
        when(clientPhoneRepository.findByPhone(anyString())).thenReturn(new ClientPhone("61991132903"));

        Client duplicateClient = new Client("Julia", "Rua j", "Jundiai", Collections.singletonList(new ClientPhone("61991132903")));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> clientService.createClient(duplicateClient));

        List<String> listErrorMessages = exception.getErrorMessages().stream()
                .map(ErrorMessage::getErrorMessage)
                .toList();

        assertTrue(listErrorMessages.contains("O 1º telefone (61991132903) já está sendo utilizado."));
    }

    @Test
    void validateClientNameIsAlreadyAdd(){
        when(clientRepository.findByName(anyString())).thenReturn(new Client("Rodrigo de Souza", "Rua j", "Jundiai", Collections.singletonList(new ClientPhone("61991132903"))));

        Client duplicateClient = new Client("Rodrigo de Souza", "Rua j", "Jundiai", Collections.singletonList(new ClientPhone("61991132903")));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> clientService.createClient(duplicateClient));

        List<String> listErrorMessages = exception.getErrorMessages().stream()
                .map(ErrorMessage::getErrorMessage)
                .toList();

        assertTrue(listErrorMessages.contains("Este cliente já foi cadastrado."));
    }

    @Test
    void validateClientNameIsBigger(){
        client.setName("Rodrigo");

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> clientService.createClient(client));

        List<String> listErrorMessages = exception.getErrorMessages().stream()
                .map(ErrorMessage::getErrorMessage)
                .toList();

        assertTrue(listErrorMessages.contains("O nome deve ter mais que 10 caracteres."));
    }

    @Test
    void validateClientWithNoName() {
        client.setName("");
        List<String> listErrors = new ArrayList<>();

        assertFalse(clientService.validateClient(client, listErrors));
        assertEquals(1, listErrors.size(), "A lista deve ter 1 erro");
        assertTrue(listErrors.contains("Nome do cliente está nulo ou vazio."));
    }

    @Test
    void validateClientWithValidData() {
        List<String> listErrors = new ArrayList<>();

        assertTrue(clientService.validateClient(client, listErrors));
        assertTrue(listErrors.isEmpty(), "A lista deve ser vazia.");
    }

    @Test
    void validateClientWithRepeatedPhoneNumbers() {


        List<ClientPhone> phones = new ArrayList<>();
        phones.add(new ClientPhone("61991132903"));
        phones.add(new ClientPhone("61991132903"));

        client.setPhones(phones);

        List<String> listErrors = new ArrayList<>();

        assertFalse(clientService.validateClient(client, listErrors));
        assertEquals(1, listErrors.size(), "A lista deve conter 1 erro.");
        assertTrue(listErrors.contains("Existem números de telefone repetidos [61991132903]. Cada número de telefone deve ser único."));
    }


    @Test
    void createInvalidClient() {
        Client invalidClient = new Client(null, "Rua j", "Jundiai", null);

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> clientService.createClient(invalidClient));

        List<String> errorMessages = exception.getErrorMessages().stream()
                .map(ErrorMessage::getErrorMessage)
                .toList();

        assertTrue(errorMessages.contains("Nome do cliente está nulo ou vazio."));
        assertTrue(errorMessages.contains("Insira no mínimo 1 telefone."));
    }

}
