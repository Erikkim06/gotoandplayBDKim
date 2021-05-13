package com.example.internkim.CSV;

public class responseCSV{
    private String message;

    public responseCSV(String message){
        this.message = message;
    }

    public String getResponseCSV(){
        return message;
    }
    public void setResponseCSV(String message){
        this.message = message;
    }
}
