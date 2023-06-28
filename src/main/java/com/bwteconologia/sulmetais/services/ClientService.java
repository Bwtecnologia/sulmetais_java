package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.models.ClientModel;
import com.bwteconologia.sulmetais.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    public Optional<ClientModel> findById(Long id) {
        return clientRepository.findById(id);
    }

    public ClientModel save(ClientModel client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
