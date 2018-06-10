package de.estate.persistence;

public class ClientCreator {

    public static void main(String[] args) {
        Client c1 = new Client(1);
        Client c2 = new Client(10);
        c1.start();
        c2.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        System.out.println("Interrupting Clients now");
        c1.interrupt();
       c2.interrupt();

        RecoveryTool rT = new RecoveryTool();
        rT.recover();

    }
}
