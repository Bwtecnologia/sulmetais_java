package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.models.PaymentConditionModel;
import com.bwteconologia.sulmetais.repositories.ConditionPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentConditionService {
    @Autowired
    private ConditionPaymentRepository conditionPaymentRepository;

    public List<PaymentConditionModel> findAll() {
        return conditionPaymentRepository.findAll();
    }

    public PaymentConditionModel save(PaymentConditionModel conditionPayment) {
        return conditionPaymentRepository.save(conditionPayment);
    }

    public void deleteById(Long id) {
        conditionPaymentRepository.deleteById(id);
    }

    public Optional<PaymentConditionModel> findById(Long id) {
        return conditionPaymentRepository.findById(id);
    }

}
