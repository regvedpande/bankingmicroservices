package com.example.banking_system.controller;

import com.example.banking_system.model.Transaction;
import com.example.banking_system.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.save(transaction);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }
}