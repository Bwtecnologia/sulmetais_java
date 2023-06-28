package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.StaticParameterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticParameterRepository extends JpaRepository<StaticParameterModel, Long> {
}
