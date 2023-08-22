package com.backendtotvs.backendtotvs.service;

import com.backendtotvs.backendtotvs.dto.ClientDto;
import com.backendtotvs.backendtotvs.exception.InvalidDataException;
import com.backendtotvs.backendtotvs.models.Client;
import com.backendtotvs.backendtotvs.models.ClientPhone;
import com.backendtotvs.backendtotvs.repository.ClientPhoneRepository;
import com.backendtotvs.backendtotvs.repository.ClientRepository;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientPhoneRepository clientPhoneRepository;

    private final ModelMapper modelMapper;

    public ClientService(ClientRepository clientRepository, ClientPhoneRepository clientPhoneRepository, ModelMapper modelMapper){
        this.clientRepository = clientRepository;
        this.clientPhoneRepository = clientPhoneRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Persist novos clientes, caso aconteça algum error é retornado uma excpetion com os erros
     * @param client
     * @return Um cliente Dto, sem repetir o cliente no telefone
     */
    public ClientDto createClient(Client client){

        List<String> errorMessages = new ArrayList<>();

        if(validateClient(client, errorMessages)){
            clientRepository.save(client);

            client.getPhones().forEach(phone -> phone.setClient(client));
            clientPhoneRepository.saveAll(client.getPhones());

            return modelMapper.map(client, ClientDto.class);
        }else{
            throw new InvalidDataException("Dados inválidos",errorMessages);
        }
    }

    /**
     *Faz a validaçao de nome do cliente e chama o metodo que faz a validaçao do telefone
     *
     * @param client
     * @param errorMessages Lista de mensagem para ser exibida no retorno do erro
     *
     * @return Se não houver error retorna true
     */
    public boolean validateClient(Client client, List<String> errorMessages){
        if(Strings.isBlank(client.getName())){
            errorMessages.add("Nome do cliente está nulo ou vazio.");
        }else{
            if(client.getName().length() <= 10){
                errorMessages.add("O nome deve ter mais que 10 caracteres.");
            }else if(clientRepository.findByName(client.getName()) != null){
                errorMessages.add("Este cliente já foi cadastrado.");
            }
        }

        validatePhones(client, errorMessages);

        return errorMessages.isEmpty();
    }

    /**
     * Validaçoes do telefone, as mensagens de erro são colocadas na lista de erros
     *
     * @param client
     * @param errorMessages Lista de mensagem para ser exibida no retorno do erro
     */
    public void validatePhones(Client client, List<String> errorMessages){

        if(client.getPhones() == null || client.getPhones().isEmpty()){
            errorMessages.add("Insira no mínimo 1 telefone.");
        }else{

            if(validatePhoneRepeat(client, errorMessages)){
                int index = 0;

                for (ClientPhone clientPhone : client.getPhones()) {
                    index++;
                    if(Strings.isBlank(clientPhone.getPhone())){
                        errorMessages.add("O " + index + "º telefone está nulo ou vazio.");
                    }else {
                        if (clientPhoneRepository.findByPhone(clientPhone.getPhone()) != null) {
                            errorMessages.add("O " + index + "º telefone (" + clientPhone.getPhone() + ") já está sendo utilizado.");
                        }
                    }
                }
            };


        }
    }

    /**
     * Valida se o proprio objeto possui telefones repetidos.
     *
     * @param client
     * @param errorMessages
     * @return retorna true, caso nao tenha numeros repetidos
     */
    public boolean validatePhoneRepeat(Client client, List<String> errorMessages){

        Set<String> uniquePhoneNumbers = new HashSet<>();
        boolean hasRepeatedPhones = false;

        for (ClientPhone phone : client.getPhones()) {
            if (uniquePhoneNumbers.contains(phone.getPhone())) {
                hasRepeatedPhones = true;
            } else {
                uniquePhoneNumbers.add(phone.getPhone());
            }
        }

        if (hasRepeatedPhones) {
            errorMessages.add("Existem números de telefone repetidos " + uniquePhoneNumbers + ". Cada número de telefone deve ser único.");
        }

        return !hasRepeatedPhones;
    }

}
