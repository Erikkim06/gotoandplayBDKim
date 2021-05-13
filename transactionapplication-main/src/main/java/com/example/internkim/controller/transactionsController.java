package com.example.internkim.controller;


import com.example.internkim.CSV.csv;
import com.example.internkim.CSV.csvService;
import com.example.internkim.CSV.responseCSV;
import com.example.internkim.dao.transactionsRepo;

import com.example.internkim.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class transactionsController {

    @Autowired
    private transactionsRepo repo;

    @Autowired
    csvService csvService;

    @PostMapping("/payments")
    public String saveTransaction(@RequestBody Transactions transaction) {
        repo.save(transaction);
        return "Transaction saved.";
    }

    @PostMapping("/payment-files")
    public ResponseEntity<responseCSV> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (csv.hasCSVFormat(file)) {
            try {
                csvService.saveFile(file);
                message = "File has been uploaded: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new responseCSV(message));
            } catch (Exception e) {
                message = "File has not been uploaded: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responseCSV(message));
            }
        }
        message = "This was not a .csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responseCSV(message));
    }

    @GetMapping("/payments")
    public List<Transactions> getAll(@RequestParam(required = false) String debtorIban) {
        List<Transactions> transactionsFromRepo = repo.findAll();
        List<Transactions> passedTransactions = new ArrayList<>();
        if (debtorIban != null) {
            for (Transactions transactions : transactionsFromRepo) {
                if (transactions.getDebtorIban().equals(debtorIban)) passedTransactions.add(transactions);
            }
            return passedTransactions;
        }
        return repo.findAll();

    }

}
