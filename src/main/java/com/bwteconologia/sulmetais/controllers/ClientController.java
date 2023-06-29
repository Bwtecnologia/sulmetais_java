package com.bwteconologia.sulmetais.controllers;

import ch.qos.logback.core.net.server.Client;
import com.bwteconologia.sulmetais.exceptions.ClientNotFoundException;
import com.bwteconologia.sulmetais.exceptions.IcmsNotFoundException;
import com.bwteconologia.sulmetais.exceptions.PaymentConditionNotFoundException;
import com.bwteconologia.sulmetais.models.ClientModel;
import com.bwteconologia.sulmetais.models.IcmsModel;
import com.bwteconologia.sulmetais.models.PaymentConditionModel;
import com.bwteconologia.sulmetais.services.ClientService;
import com.bwteconologia.sulmetais.services.IcmsService;
import com.bwteconologia.sulmetais.services.PaymentConditionService;
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

    @Autowired
    private IcmsService icmsService;

    @Autowired
    private PaymentConditionService paymentConditionService;

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
    public ResponseEntity<ClientModel> save(@RequestBody ClientModel client) {

        Optional<IcmsModel> icmsOptional = icmsService.findById(client.getIcms().getId());
        if (icmsOptional.isEmpty()) throw new IcmsNotFoundException("Icms with id " + client.getIcms().getId() + " was not found");

        Optional<PaymentConditionModel> paymentConditionOptional = paymentConditionService.findById(client.getPaymentCondition().getId());
        if (paymentConditionOptional.isEmpty()) throw new PaymentConditionNotFoundException("Payment condition with id " + client.getPaymentCondition().getId() + " was not found");

//      if (Optional.ofNullable(client).isEmpty()) throw new
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ClientModel> update(@RequestBody ClientModel client,@PathVariable Long id) {

        Optional<IcmsModel> icmsOptional = icmsService.findById(client.getIcms().getId());
        if (icmsOptional.isEmpty()) throw new IcmsNotFoundException("Icms with id " + client.getIcms().getId() + " was not found");

        Optional<PaymentConditionModel> paymentConditionOptional = paymentConditionService.findById(client.getPaymentCondition().getId());
        if (paymentConditionOptional.isEmpty()) throw new PaymentConditionNotFoundException("Payment condition with id " + client.getPaymentCondition().getId() + " was not found");

        if(clientService.findById(id).isEmpty()) throw new ClientNotFoundException("Client with id " + id + " was not found");

        return ResponseEntity.ok(clientService.save(client));
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        if(clientService.findById(id).isEmpty()) throw new ClientNotFoundException("Client with id " + id + " was not found");

        clientService.deleteById(id);
        return ResponseEntity.ok("Client has been deleted");
    }

}
