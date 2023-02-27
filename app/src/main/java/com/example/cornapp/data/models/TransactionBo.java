package com.example.cornapp.data.models;

public class TransactionBo {
    private String message, user_id, token;
    private int amount;

    public TransactionBo(String message, String user_id, String token, int amount){
        this.message = message;
        this.user_id = user_id;
        this.token = token;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getUserId() {
        return user_id;
    }

    public String getTransactionToken() {
        return token;
    }

    public String getMessage(){
        return message;
    }

}
