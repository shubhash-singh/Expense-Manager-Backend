package com.example.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.expensetracker.dto.ExpenseRequest;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(ExpenseRequest expenseRequest) {
        Expense expense = new Expense();
        copyRequestToExpense(expenseRequest, expense);
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpenses(String category, LocalDate startDate, LocalDate endDate) {
        // No filters provided, return everything
        if (category == null && startDate == null && endDate == null) {
            return expenseRepository.findAll();
        }

        // Handle category only
        if (category != null && startDate == null && endDate == null) {
            return expenseRepository.findByCategory(category);
        }

        // Handle date range only by filling null bounds with extremes
        LocalDate safeStart = startDate != null ? startDate : LocalDate.of(1970, 1, 1);
        LocalDate safeEnd = endDate != null ? endDate : LocalDate.of(3000, 1, 1);

        if (category == null) {
            return expenseRepository.findByDateBetween(safeStart, safeEnd);
        }

        return expenseRepository.findByCategoryAndDateBetween(category, safeStart, safeEnd);
    }

    public Optional<Expense> updateExpense(String id, ExpenseRequest expenseRequest) {
        return expenseRepository.findById(id).map(existingExpense -> {
            copyRequestToExpense(expenseRequest, existingExpense);
            return expenseRepository.save(existingExpense);
        });
    }

    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

    private void copyRequestToExpense(ExpenseRequest expenseRequest, Expense expense) {
        expense.setAmount(expenseRequest.getAmount());
        expense.setDescription(expenseRequest.getDescription());
        expense.setDate(expenseRequest.getDate());
        expense.setCategory(expenseRequest.getCategory());
        expense.setUserId(expenseRequest.getUserId());
    }
}

