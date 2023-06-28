package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.PaymentConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionPaymentRepository extends JpaRepository<PaymentConditionModel, Long>{
}
