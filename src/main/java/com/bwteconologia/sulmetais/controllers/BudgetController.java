package com.bwteconologia.sulmetais.controllers;

import com.bwteconologia.sulmetais.models.BudgetModel;
import com.bwteconologia.sulmetais.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BudgetController {
    @Autowired
     BudgetService budgetService;

    @GetMapping(value = "/budget")
    public List<BudgetModel> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @GetMapping(value = "/budget/{id}")
    public ResponseEntity<BudgetModel> getBudgetById(@PathVariable int id) {
        Optional<BudgetModel> budget = budgetService.findById(id);
        if (budget.isPresent()) {
            return ResponseEntity.ok(budget.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/budget")
    public ResponseEntity<BudgetModel> createBudget(@RequestBody BudgetModel budget) {
        BudgetModel createdBudget = budgetService.save(budget);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBudget.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdBudget);
    }

    @PutMapping(value = "/budget/{id}")
    public ResponseEntity<BudgetModel> updateBudget(@PathVariable int id, @RequestBody BudgetModel budget) {
        Optional<BudgetModel> existingBudget = budgetService.findById(id);
        if (existingBudget.isPresent()) {
            BudgetModel updatedBudget = budgetService.save(budget);
            return ResponseEntity.ok(updatedBudget);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/budget/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable int id) {
        Optional<BudgetModel> budget = budgetService.findById(id);
        if (budget.isPresent()) {
            budgetService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
