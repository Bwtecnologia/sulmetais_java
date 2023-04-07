package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.exceptions.UnitNotFoundException;
import com.bwteconologia.sulmetais.exceptions.UserAlreadyExistsException;
import com.bwteconologia.sulmetais.models.ResponseObjectModel;
import com.bwteconologia.sulmetais.models.UnitModel;
import com.bwteconologia.sulmetais.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UnitModel>> getAllUnits() {
        List<UnitModel> units = unitService.getAllUnits();
        return ResponseEntity.ok().body(units);
    }

    @GetMapping(value = "/units/{id}")
    public ResponseEntity<UnitModel> updateUnit(@PathVariable("id") Long id) {
        UnitModel unit = unitService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new UnitNotFoundException("Unit with " + id + " is Not Found!"));
        return ResponseEntity.ok(unit);
    }
    @PostMapping(value = "/units")
    public ResponseEntity<UnitModel> addUnit(@RequestBody UnitModel unit) {
        Optional<UnitModel> existsUnit = unitService.findByUnitSize(unit.getUnitSize());
        if (existsUnit.isPresent()) {
            throw new UserAlreadyExistsException("Unit already exists");
        }
        UnitModel savedUnit = unitService.save(unit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUnit);
    }

    @PutMapping(value = "/units/{id}")
    public ResponseEntity<UnitModel> updateUnit(@PathVariable("id") int id, @RequestBody UnitModel unitUpdated) {
        UnitModel unit = unitService.findById(id)
                .orElseThrow(() -> new UnitNotFoundException("Unit with " + id + " is Not Found!"));

        unit.setUnitSize(unitUpdated.getUnitSize());
        unit.setUpdatedAt(new Date());

        UnitModel updatedUnit = unitService.save(unit);

        return ResponseEntity.ok().body(updatedUnit);
    }
    @DeleteMapping(value = "/units/{id}")
    public ResponseEntity<ResponseObjectModel> deleteUnit(@PathVariable("id") Long id) {
        UnitModel unit = unitService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new UnitNotFoundException("Unit with " + id + " is Not Found!"));
        unitService.deleteById(Math.toIntExact(unit.getId()));
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id,"Unit with ID :"+id+" is deleted ");
        return ResponseEntity.ok(responseObjectModel);
    }

}

