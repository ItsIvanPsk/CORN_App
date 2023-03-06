package com.example.cornapp.data.models;

public class UserTransactionBo {

    private String originName, destinationName, timeSetup, timeAccepted;
    private int origin, destination, amount, accepted;


    public UserTransactionBo(
            String originName,
            String destinationName,
            String timeSetup,
            String timeAccepted,
            int origin,
            int destination,
            int amount,
            int accepted
    ) {
        this.originName = originName;
        this.destinationName = destinationName;
        this.timeSetup = timeSetup;
        this.timeAccepted = timeAccepted;
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
        this.accepted = accepted;
    }
    public UserTransactionBo() {  }


    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getTimeSetup() {
        return timeSetup;
    }

    public void setTimeSetup(String timeSetup) {
        this.timeSetup = timeSetup;
    }

    public String getTimeAccepted() {
        return timeAccepted;
    }

    public void setTimeAccepted(String timeAccepted) {
        this.timeAccepted = timeAccepted;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }
}
