package com.example.internkim.model;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Data

public class Transactions {

    /**
     * @param id A random ID given to a transaction
     * @param amount The numeric amount given in a transaction. Datatype BigDecimal to accommodate decimals.
     * @param debtorIban A string to determine the transactions IBAN code. The application accepts IBAN codes only from Baltic countries (Estonia, Latvia, Lithuania).
     * @param createdAt The time, when the transaction was made.
     */

    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    @Range(min=4, max= 99999999)
    private BigDecimal amount;

    @Pattern(regexp = "^(EE|LT)\\d{18}|LV\\d{2}[A-Z]{4}\\d{13}$") //Regex-i jaoks kasutatud reeglid on võetud lehelt https://pangaliit.ee/arveldused/iban
    private String debtorIban;
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    public Transactions(UUID id, BigDecimal amount, String debtorIban, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.debtorIban = debtorIban;
        this.createdAt = createdAt;
    }
    public Transactions(){
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        if(debtorIban.matches("^(EE|LT)\\d{18}|LV\\d{2}[A-Z]{4}\\d{13}$"))
        this.debtorIban = debtorIban;
        else{
            this.debtorIban=null;
        }
    }
}
