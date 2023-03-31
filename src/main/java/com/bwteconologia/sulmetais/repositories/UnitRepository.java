package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.UnitModel;
import com.bwteconologia.sulmetais.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UnitRepository extends JpaRepository<UnitModel, Integer> {
    Optional<UnitModel> findByUnitSize(String unitSize);
}
