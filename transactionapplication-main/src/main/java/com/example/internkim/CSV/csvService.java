package com.example.internkim.CSV;

import com.example.internkim.dao.transactionsRepo;
import com.example.internkim.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class csvService {
    @Autowired
    transactionsRepo repo;

    /**
     * A method to save the transactions in a repository.
     * @param file The .csv file from where data will be imported.
     */
    public void saveFile(MultipartFile file){
        try{
            List<Transactions> Transactionss = csv.csvTransactions(file.getInputStream());
            repo.saveAll(Transactionss);
        } catch (IOException e){
            throw new RuntimeException("Error while storing .csv data: " + e.getMessage());
        }
    }
    public List<Transactions> getTransactionss(){
        return repo.findAll();
    }
}
