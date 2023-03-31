package com.bwteconologia.sulmetais.interfaces;

import com.bwteconologia.sulmetais.models.UnitModel;

import java.util.List;
import java.util.Optional;

public interface IUnit {
    List<UnitModel> getAllUnits();
    Optional<UnitModel> findById(int id);
    UnitModel save(UnitModel unity);
    Optional<UnitModel> findByUnitSize(String unitSize);
    void deleteById(int id);
}
