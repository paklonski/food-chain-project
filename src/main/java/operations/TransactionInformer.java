package operations;

import java.io.*;
import java.util.*;

import chainParticipants.*;
import static simulation.Main.LOGGER;

public class TransactionInformer implements Observable {

    private List<Transaction> transactions = new ArrayList<>();
    private List<Party> parties = new ArrayList<>();
    private List<String> securityReports;

    public void attach(Party party) {                                       // a part of Observer pattern
        parties.add(party);
    }

    public void detach(Party party) {                                       // a part of Observer pattern
        parties.remove(party);
    }

    /**
     * Uses to inform all parties about some action.
     * @param transaction
     */
    public void notify(Transaction transaction) {
        transactions.add(transaction);
        for (Party party : parties) {
            party.updateTransactions(transaction);
        }
    }

    public void addToSecurityReport(String report) {
        securityReports.add(report);
    }

    public void makeSecurityReport() {
        if (securityReports == null) {
            System.out.println("ANY DOUBLE SPENDING PROBLEM HAS NOT FOUNDED.");
        } else {
            for (String report : securityReports) {
                System.out.println(report);
            }
        }
    }

    public void makeTextSecurityReport() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("SecurityReport.txt"), "utf-8"))) {
            writer.write("-------------------- SECURITY REPORT --------------------\n");
            if (securityReports == null) {
                writer.write("ANY DOUBLE SPENDING PROBLEM HAS NOT FOUNDED. \n");
            }
            for (String report : securityReports) {
                writer.write(report + "\n");
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Uses to make all transactions report.
     */
    public void makeReport() {
        System.out.println("-------------------- TRANSACTION INFORMER REPORT --------------------");
        for (Transaction transaction : this.transactions) {
            transaction.getTransactionInfo();
        }
        System.out.println("---------------------------------------------------------------------");
    }

    /**
     * Uses to generate text report with all transactions.
     */
    public void generateTextReport() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("TransactionReport.txt"), "utf-8"))) {
            writer.write("-------------------- TRANSACTION INFORMER REPORT --------------------\n");
            for (Transaction transaction : this.transactions) {
                writer.write(transaction.getInfoForText() + "\n");
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
