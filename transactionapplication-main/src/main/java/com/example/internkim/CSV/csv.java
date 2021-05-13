package com.example.internkim.CSV;

import com.example.internkim.model.Transactions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class csv {
    /**
     * @param TYPE The type of file that the application accepts.
     * @param HEADERS The headers of the .csv file that the application accepted.
     */
    public static String TYPE = "text/csv";
    static String[] HEADERS = { "amount", "debtorIban"};

    /**
     * A method to check, whether the given file is a .csv file.
     * @param file The file, that will try to pass the check
     * @return A boolean value that shows whether the file is a .csv file or not. Returns true if the file is .csv, if not then false.
     */
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /**
     * A method that creates an ArrayList of transactions taken from the .csv file.
     * @return Returns the ArrayList of transactions imported from the .csv file.
     */
    public static List<Transactions> csvTransactions(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Transactions> transactions = new ArrayList<Transactions>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Transactions transaction= new Transactions(
                        UUID.randomUUID(),
                        BigDecimal.valueOf(Long.parseLong(csvRecord.get("amount"))),
                        csvRecord.get("debtorIban"),
                        LocalDateTime.now(ZoneOffset.UTC)
                        );
                if((transaction.getAmount().compareTo(BigDecimal.valueOf(0.0))>0)&& transaction.getDebtorIban().matches("^(EE|LT)\\d{18}|LV\\d{2}[A-Z]{4}\\d{13}$")){
                    transactions.add(transaction);
                }
            }

            return transactions;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}