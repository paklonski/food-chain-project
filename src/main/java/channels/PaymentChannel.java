package channels;

import operations.*;
import chainParticipants.*;

import java.util.*;

import static simulation.Main.LOGGER;

public final class PaymentChannel {

    private static PaymentChannel paymentChannel;
    private List<Party> trustedParticipants;
    private List<Party> currentParticipants = new ArrayList<>();
    private TransactionInformer transactionInformer;

    private Party applicant = null;
    private Party executor = null;
    private int amount = 0;

    private PaymentChannel(List<Party> trustedParticipants, TransactionInformer transactionInformer) {
        this.transactionInformer = transactionInformer;
        this.trustedParticipants = trustedParticipants;
    }

    public static PaymentChannel getPaymentChannel(List<Party> trustedParticipants, TransactionInformer transactionInformer) {
        if (paymentChannel == null) {
            paymentChannel = new PaymentChannel(trustedParticipants, transactionInformer);
        }
        return paymentChannel;
    }

    public static PaymentChannel getPaymentChannel() {
        return paymentChannel;
    }

    /**
     * Registers the chain participant as current, if it is trusted.
     *
     * @param party the chain participant
     */
    public void register(Party party) {
        if (trustedParticipants.contains(party)) {
            currentParticipants.add(party);
        } else {
            LOGGER.warning("PAYMENT CHANNEL : Access to the payment channel for this type " +
                    "of chain participant is denied.");
        }
    }

    /**
     * Unregisters the chain participant from the list of current.
     *
     * @param party the chain participant
     */
    public void unregister(Party party) {
        if (currentParticipants.contains(party) && !party.equals(this.applicant) && !party.equals(this.executor)) {
            currentParticipants.remove(party);
        } else {
            LOGGER.warning("PAYMENT CHANNEL : This party is not a participant of the payment channel" +
                    " or you need to free the payment channel.");
        }
    }

    private boolean isRegister(Party party) {
        return currentParticipants.contains(party);
    }

    /**
     * Transfers the money lying inside the channel to the executor.
     *
     * @param executor the executor of the request
     * @return the amount
     */
    public int takeMoney(Party executor) {
        if (this.executor == executor) {
            int amount = this.amount;
            this.amount = 0;
            return amount;
        }
        return 0;
    }

    /**
     * Invests money in the channel for subsequent transfer to the executor.
     *
     * @param applicant the applicant of the request
     * @param money     the amount
     * @return true if the money was invested
     */
    public boolean putMoney(Party applicant, int money) {
        if (this.applicant == applicant) {
            this.amount = money;
            return true;
        }
        return false;
    }

    /**
     * Starts the private and safety process of exchanging money.
     *
     * @param price the price
     */
    private void makePayment(int price) {
        this.applicant.payMoney(price);
        this.executor.getProfit();
    }

    private void resetChannel() {
        this.applicant = null;
        this.executor = null;
    }

    /**
     * Request for money exchange between the applicant and the executor.
     *
     * @param applicant the applicant
     * @param executor  the executor
     * @param price     the price of the product
     */
    public void makePayment(Party applicant, Party executor, int price) {
        if (isRegister(applicant) && isRegister(executor)) {
            this.applicant = applicant;
            this.executor = executor;
            if (this.applicant.isAgreeToPay(amount)) {
                makePayment(price);
            } else {
                System.out.println("There are insufficient funds in the account.");
            }
            resetChannel();
        } else {
            LOGGER.warning("One of the chain participants is not registered. The payment is canceled.");
        }
    }
}
