package com.example.internkim.dao;

import com.example.internkim.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface transactionsRepo extends JpaRepository<Transactions, Integer> {
    List<Transactions> findByDebtorIban(String debtorIban);

}
