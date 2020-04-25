package operations;

import product.*;
import chainParticipants.*;

public class Transaction {

    private Request request;
    private Party executor;
    private final int previousHash;
    private final int hash = hashCode();

    public Transaction(Request request, Party executor, int previousHash) {
        this.request = request;
        this.executor = executor;
        this.previousHash = previousHash;
    }

    /**
     * Uses to generate string for transactions report.
     *
     * @return line for transactions report
     */
    public String getInfoForText() {
        return "TRANSACTION: " + "applicant = " + request.getApplicant() +
                ", executor = " + executor + ", foodEnum = " + request.getFoodEnum() +
                ", quantity = " + request.getQuantity() + ", operationType = " + request.getOperationType() +
                ", price = " + request.getPrice() + '.';
    }

    /**
     * Uses to write info from transaction for transactions report.
     */
    public void getTransactionInfo() {
        System.out.println(getInfoForText());
    }

    public int getHash() {
        return hash;
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public OperationEnum getOperationType() {
        return request.getOperationType();
    }

    public String getSender() {
        return executor.getName();
    }

    public FoodEnum getProduct() {
        return request.getFoodEnum();
    }

    public int getQuantity() {
        return request.getQuantity();
    }
}
