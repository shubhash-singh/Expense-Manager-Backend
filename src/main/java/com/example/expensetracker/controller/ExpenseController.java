package com.example.expensetracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.dto.ExpenseRequest;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseRequest expenseRequest) {
        // Create a new expense using the service and return it
        Expense savedExpense = expenseService.addExpense(expenseRequest);
        return ResponseEntity.ok(savedExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // Pass any provided filters down to the service
        List<Expense> expenses = expenseService.getExpenses(category, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable String id, @RequestBody ExpenseRequest expenseRequest) {
        // Try to update the expense, return 404 if the id is unknown
        return expenseService.updateExpense(id, expenseRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        // Delete the expense; MongoRepository handles missing ids gracefully
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}

