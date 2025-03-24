package com.example.banking_system.service;

import com.example.banking_system.event.TransactionEvent;
import com.example.banking_system.model.Transaction;
import com.example.banking_system.repository.TransactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionService(TransactionRepository transactionRepository, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Transaction save(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        try {
            // Comment out for testing:
            // kafkaTemplate.send("transaction-events", new TransactionEvent(savedTransaction));
        } catch (Exception e) {
            // Log the exception if needed
            System.err.println("Kafka send failed: " + e.getMessage());
        }
        return savedTransaction;
    }



    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}