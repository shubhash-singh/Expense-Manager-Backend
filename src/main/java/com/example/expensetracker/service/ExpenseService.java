package com.example.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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

    public List<Expense> getExpenses(String category, String startDate, String endDate) {
        List<Expense> baseExpenses;

        if (category != null && !category.isEmpty()) {
            baseExpenses = expenseRepository.findByCategory(category);
        } else {
            baseExpenses = expenseRepository.findAll();
        }

        LocalDate parsedStart = parseDate(startDate);
        LocalDate parsedEnd = parseDate(endDate);

        if (parsedStart == null && parsedEnd == null) {
            return baseExpenses;
        }

        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : baseExpenses) {
            LocalDate expenseDate = parseDate(expense.getDate());
            if (expenseDate == null) {
                continue;
            }
            boolean afterStart = parsedStart == null || !expenseDate.isBefore(parsedStart);
            boolean beforeEnd = parsedEnd == null || !expenseDate.isAfter(parsedEnd);
            if (afterStart && beforeEnd) {
                filteredExpenses.add(expense);
            }
        }

        return filteredExpenses;
    }

    public Optional<Expense> updateExpense(String id, ExpenseRequest expenseRequest) {
        return expenseRepository.findById(id).map(existingExpense -> {
            copyRequestToExpense(expenseRequest, existingExpense);
            return expenseRepository.save(existingExpense);
        });
    }

    public boolean deleteExpense(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Expense id must not be blank");
        }
        if (!expenseRepository.existsById(id)) {
            return false;
        }
        expenseRepository.deleteById(id);
        return true;
    }

    private void copyRequestToExpense(ExpenseRequest expenseRequest, Expense expense) {
        expense.setAmount(expenseRequest.getAmount());
        expense.setDescription(expenseRequest.getDescription());
        expense.setDate(expenseRequest.getDate());
        expense.setCategory(expenseRequest.getCategory());
        expense.setUserId(expenseRequest.getUserId());
    }
    private LocalDate parseDate(String rawDate) {
        if (rawDate == null || rawDate.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(rawDate);
        } catch (Exception e) {
            return null;
        }
    }
}

