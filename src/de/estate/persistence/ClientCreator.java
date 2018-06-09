package de.estate.persistence;

public class ClientCreator {

    public static void main(String[] args) {
        Client c1 = new Client();
        Client c2 = new Client();
        c1.start();
        c2.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        c1.interrupt();
        c2.interrupt();


    }
