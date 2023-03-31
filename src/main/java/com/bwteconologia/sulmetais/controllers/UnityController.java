package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.UnitNotFoundException;
import com.bwteconologia.sulmetais.exceptions.UserAlreadyExistsException;
import com.bwteconologia.sulmetais.models.UnitModel;
import com.bwteconologia.sulmetais.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UnityController {
    @Autowired
    UnitService unitService;


    @GetMapping(value = "/units")
    public List<UnitModel> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping(value = "/units/{id}")
    public UnitModel updateUnit(@PathVariable("id") int id) {
        UnitModel unit = unitService.findById(id)
                .orElseThrow(() -> new UnitNotFoundException("Unit with " + id + " is Not Found!"));
        return unit;
    }

    @PostMapping(value = "/units")
    public UnitModel addUnit(@RequestBody UnitModel unit) {
        Optional<UnitModel> existsUnit = unitService.findByUnitSize(unit.getUnitSize());
        if(existsUnit != null){
            throw new UserAlreadyExistsException("User already exists");
        }
        return unitService.save(unit);
    }

    @PutMapping(value = "/units/{id}")
    public UnitModel updateUnit(@PathVariable("id") int id, @RequestBody UnitModel unitUpdated) {
        UnitModel unit = unitService.findById(id)
                .orElseThrow(() -> new UnitNotFoundException("Unit with " + id + " is Not Found!"));
        unit.setUnitSize(unitUpdated.getUnitSize());
        unit.setUpdatedAt(new Date());

        return unitService.save(unit);
    }
    @DeleteMapping(value = "/units/{id}")
    public String deleteUnit(@PathVariable("id") int id){
        UnitModel user = unitService.findById(id)
                .orElseThrow(() -> new UnitNotFoundException("Unit with " + id + " is Not Found!"));
        unitService.deleteById(user.getId());
        return "Unit with ID :"+id+" is deleted";
    }

}

