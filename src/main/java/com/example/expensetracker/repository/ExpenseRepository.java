package com.example.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.model.Expense;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByCategory(String category);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByCategoryAndDateBetween(String category, LocalDate startDate, LocalDate endDate);
}

