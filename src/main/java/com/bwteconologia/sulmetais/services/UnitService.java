package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IUnit;
import com.bwteconologia.sulmetais.models.UnitModel;
import com.bwteconologia.sulmetais.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UnitService implements IUnit {
    UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository){
        this.unitRepository = unitRepository;
    }
    @Override
    public List<UnitModel> getAllUnits() {
        return unitRepository.findAll();
    }

    @Override
    public Optional<UnitModel> findById(int id) {
        return unitRepository.findById(id);
    }

    @Override
    public UnitModel save(UnitModel unity) {
        return unitRepository.save(unity);
    }

    @Override
    public Optional<UnitModel> findByUnitSize(String unitSize) {
        return unitRepository.findByUnitSize(unitSize);
    }

    @Override
    public void deleteById(int id) {
        unitRepository.deleteById(id);
    }
}
