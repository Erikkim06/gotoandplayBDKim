package com.example.internkim.tests;

import com.example.internkim.CSV.csvService;
import com.example.internkim.InternkimApplication;
import com.example.internkim.controller.transactionsController;
import com.example.internkim.dao.transactionsRepo;
import com.example.internkim.model.Transactions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.math.BigDecimal;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={InternkimApplication.class, transactionsController.class})
@WebMvcTest(transactionsController.class)
@EnableJpaRepositories("com.example.repositories")
public class transactionTest {
    
    @Test
    public void testEEIban(){
        Transactions transaction = new Transactions();
        transaction.setDebtorIban("EE123456789123456789");
        assertEquals("EE123456789123456789",transaction.getDebtorIban());
    }
    @Test
    public void testLVIban(){
        Transactions transaction = new Transactions();
        transaction.setDebtorIban("LV74KDMF3456928374691");
        assertEquals("LV74KDMF3456928374691",transaction.getDebtorIban());
    }
    @Test
    public void testLTIban(){
        Transactions transaction = new Transactions();
        transaction.setDebtorIban("LT123456789123456789");
        assertEquals("LT123456789123456789",transaction.getDebtorIban());
    }

}
