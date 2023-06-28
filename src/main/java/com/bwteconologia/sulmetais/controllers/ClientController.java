package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.ClientNotFoundException;
import com.bwteconologia.sulmetais.models.ClientModel;
import com.bwteconologia.sulmetais.services.ClientService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public ResponseEntity<List<ClientModel>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientModel> findById(@PathVariable Long id) {

        Optional<ClientModel> clientOptional = clientService.findById(id);

        if (clientOptional.isEmpty()) throw new ClientNotFoundException("Client with id " + id + " was not found");
        return ResponseEntity.ok(clientOptional.get());
    }

    @PostMapping("/client")
    public ResponseEntity<ClientModel> save(ClientModel client) {
//        if (Optional.ofNullable(client).isEmpty()) throw new
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ClientModel> update(ClientModel client, Long id) {

        if(clientService.findById(id).isEmpty()) throw new ClientNotFoundException("Client with id " + id + " was not found");

        return ResponseEntity.ok(clientService.save(client));
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<String> deleteById(Long id) {

        if(clientService.findById(id).isEmpty()) throw new ClientNotFoundException("Client with id " + id + " was not found");

        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
