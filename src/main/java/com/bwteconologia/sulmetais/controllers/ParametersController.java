package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.IcmsNotFoundException;
import com.bwteconologia.sulmetais.exceptions.PaymentConditionNotFoundException;
import com.bwteconologia.sulmetais.models.IcmsModel;
import com.bwteconologia.sulmetais.models.PaymentConditionModel;
import com.bwteconologia.sulmetais.models.StaticParameterModel;
import com.bwteconologia.sulmetais.services.PaymentConditionService;
import com.bwteconologia.sulmetais.services.IcmsService;
import com.bwteconologia.sulmetais.services.StaticParameterService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ParametersController {

    @Autowired
    private StaticParameterService staticParameterService;

    @Autowired
    private PaymentConditionService paymentConditionService;

    @Autowired
    private IcmsService icmsService;

    @GetMapping("/parameters/static")
    private ResponseEntity<StaticParameterModel> getParameters() {
        return ResponseEntity.ok(staticParameterService.findById(1L).get());
    }

    @PutMapping("/parameters/static")
    private ResponseEntity<StaticParameterModel> updateParameters(StaticParameterModel staticParameterModel) {
        staticParameterModel.setId(1L);
        return ResponseEntity.ok(staticParameterService.save(staticParameterModel));
    }

    @GetMapping("/parameters/payment")
    private ResponseEntity<List<PaymentConditionModel>> getAllPaymentConditions() {
        return ResponseEntity.ok(paymentConditionService.findAll());
    }

    @GetMapping("/parameters/payment/{id}")
    private ResponseEntity<PaymentConditionModel> getPaymentConditionById(@PathVariable Long id) {
        Optional<PaymentConditionModel> paymentConditionModelOptional = paymentConditionService.findById(id);

        if (paymentConditionModelOptional.isEmpty()) throw new PaymentConditionNotFoundException
                ("The payment condition with id " + id + " was not found");

        return ResponseEntity.ok(paymentConditionModelOptional.get());
    }

    @PostMapping("/parameters/payment")
    private ResponseEntity<PaymentConditionModel> insertPaymentCondition(PaymentConditionModel paymentConditionModel) {
        return ResponseEntity.ok(paymentConditionService.save(paymentConditionModel));
    }

    @PutMapping("/parameters/payment/{id}")
    private ResponseEntity<PaymentConditionModel> updatePaymentCondition
            (PaymentConditionModel paymentConditionModel, @PathVariable Long id) {

        Optional<PaymentConditionModel> paymentConditionModelOptional = paymentConditionService.findById(id);

        if (paymentConditionModelOptional.isEmpty()) throw new PaymentConditionNotFoundException
                ("The payment condition with id " + id + " was not found");

        paymentConditionModel.setId(id);

        return ResponseEntity.ok(paymentConditionService.save(paymentConditionModel));
    }

    @DeleteMapping("/parameters/payment/{id}")
    private ResponseEntity<Void> deletePaymentCondition(@PathVariable Long id) {

        Optional<PaymentConditionModel> paymentConditionModelOptional = paymentConditionService.findById(id);

        if (paymentConditionModelOptional.isEmpty()) throw new PaymentConditionNotFoundException
                ("The payment condition with id " + id + " was not found");

        paymentConditionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/parameters/icms")
    private ResponseEntity<List<IcmsModel>> getIcms() {
        return ResponseEntity.ok(icmsService.findAll());
    }

    @GetMapping("/parameters/icms/{id}")
    private ResponseEntity<IcmsModel> getIcmsById(@PathVariable Long id) {
        Optional<IcmsModel> icmsModelOptional = icmsService.findById(id);

        if (icmsModelOptional.isEmpty()) throw new IcmsNotFoundException
                ("The icms with id " + id + " was not found");

        return ResponseEntity.ok(icmsModelOptional.get());
    }

    @PostMapping("/parameters/icms")
    private ResponseEntity<IcmsModel> insertIcms(IcmsModel icmsModel) {
        return ResponseEntity.ok(icmsService.save(icmsModel));
    }

    @PutMapping("/parameters/icms/{id}")
    private ResponseEntity<IcmsModel> updateIcms(IcmsModel icmsModel, @PathVariable Long id) {

        Optional<IcmsModel> icmsModelOptional = icmsService.findById(id);

        if (icmsModelOptional.isEmpty()) throw new IcmsNotFoundException
                ("The icms with id " + id + " was not found");

        icmsModel.setId(id);
        return ResponseEntity.ok(icmsService.save(icmsModel));
    }

    @DeleteMapping("/parameters/icms/{id}")
    private ResponseEntity<String> deleteIcms(@PathVariable Long id) {

        Optional<IcmsModel> icmsModelOptional = icmsService.findById(id);

        if (icmsModelOptional.isEmpty()) throw new IcmsNotFoundException
                ("The icms with id " + id + " was not found");

        icmsService.deleteById(id);
        return ResponseEntity.ok("The icms with id " + id + " was deleted");
    }

    @PostConstruct
    public void insertWhenStaticParametersIsNull() {
        Optional<StaticParameterModel> staticParameterModelOptional = staticParameterService.findById(1L);

        if (staticParameterModelOptional.isEmpty()) {
            StaticParameterModel staticParameter = new StaticParameterModel();
            staticParameter.setIpiPercentage(0.0);
            staticParameter.setPisPercentage(0.0);
            staticParameter.setCofinsPercentage(0.0);
            staticParameter.setComissionPercentage(0.0);
            staticParameter.setDeliveryPercentage(0.0);

            staticParameterService.save(staticParameter);
        }
    }
}
