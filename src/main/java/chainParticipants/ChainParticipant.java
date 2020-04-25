package chainParticipants;

import product.*;
import visitor.*;
import channels.*;
import operations.*;

import java.util.*;

import static simulation.Main.LOGGER;

/**
 * An abstract class describes the general state and behavior
 * of a participant in a chain, including operations such as accepting a product,
 * fulfilling a request, accepting payment, and others.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public abstract class ChainParticipant implements Party {

    private String name;
    private int cashAccount;
    private TransactionInformer transactionInformer;
    private List<Transaction> transactions = new ArrayList<>();
    private HashMap<FoodEnum, Integer> prices = new HashMap<>();

    public ChainParticipant(String name, int cashAccount, TransactionInformer transactionInformer) {
        this.name = name;
        this.cashAccount = cashAccount;
        this.transactionInformer = transactionInformer;
    }

    public abstract OperationEnum getOperationType();

    public abstract boolean isAgreeToExecute(Request request);

    public abstract FoodEntity process(Request request);

    public abstract void addFood(FoodEntity foodEntity);

    public abstract void registerToTheChannel();

    /**
     * Function is used for fill prices of the party.
     *
     * @param visitor
     */
    public abstract void accept(Visitor visitor);

    /**
     * Function is used for getting prices of something from somebody.
     *
     * @param ingredient
     * @param quantity
     * @return price
     */
    public int askPrice(FoodEnum ingredient, int quantity) {
        for (FoodEnum food : prices.keySet()) {
            if (food == ingredient) {
                return prices.get(food) * quantity;
            }
        }
        return 0;
    }

    public void put(FoodEnum foodEnum, int price) {
        prices.put(foodEnum, price);
    }

    /**
     * Creates a request and sends it to the desired channel, depending on the type of product.
     *
     * @param foodEnum the name of the product
     * @param quantity the quantity
     */
    public void sendRequest(FoodEnum foodEnum, int quantity) {
        Request request = new Request(this, foodEnum, quantity, getOperationType());
        if (MeatChannel.getMeatChannel().getAllowedEntities().contains(foodEnum)) {
            MeatChannel.getMeatChannel().makeRequest(request);
        } else if (VegetableChannel.getVegetableChannel().getAllowedEntities().contains(foodEnum)) {
            VegetableChannel.getVegetableChannel().makeRequest(request);
        } else if (ReadyMealChannel.getReadyMealChannel().getAllowedEntities().contains(foodEnum)) {
            ReadyMealChannel.getReadyMealChannel().makeRequest(request);
        } else {
            System.out.println("The request cannot be made either in the meat or in the vegetable channel.");
        }
    }

    public int getLastHash() {
        if (transactions.size() == 0) {
            return 0;
        }
        return transactions.get(transactions.size() - 1).getHash();
    }

    /**
     * Updates the list of transactions for each of the chain members.
     *
     * @param transaction completed transaction
     */
    public void updateTransactions(Transaction transaction) {
        if (transactions.size() != 0 && transaction.getPreviousHash() != transactions.get(transactions.size() - 1).getHash()) {
            LOGGER.warning("LAST TRANSACTION HASH WAS CHANGED");
        }
        doubleSpending(transaction);
        transactions.add(transaction);
    }

    private void doubleSpending(Transaction transaction) {
        for (Transaction transactionInList : transactions) {
            if (transactionInList.getHash() == transaction.getHash()) {
                LOGGER.warning("DOUBLE SPENDING PROBLEM FOR TRANSACTION " + transaction);
                transactionInformer.addToSecurityReport("Hash of transaction " + transactionInList +
                        " is similar to hash of " + transaction);
            }
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Ask if a chain participant has the means to pay for the request.
     *
     * @param price the price of the product
     * @return true if a chain participant has enough money
     */
    public boolean isAgreeToPay(int price) {
        return price <= cashAccount;
    }

    public void payMoney(int price) {
        if (PaymentChannel.getPaymentChannel().putMoney(this, price)) {
            cashAccount -= price;
        }
    }

    public void getProfit() {
        cashAccount += PaymentChannel.getPaymentChannel().takeMoney(this);
    }

    public int getCashAccount() {
        return cashAccount;
    }

    public Iterator getIterator() {
        return new PartiesReportIterator();
    }

    private class PartiesReportIterator implements Iterator {

        int index = 0;

        /**
         * uses to check if list of transactions has next transaction
         *
         * @return true if has next transaction, false otherwise
         */
        public boolean hasNext() {
            return index < transactions.size();
        }

        /**
         * uses to take text for party report
         *
         * @return ready line for party report,null if list of transactions doesn't have next object or
         * if it is another participants transaction
         */
        public String next() {
            Transaction transaction = transactions.get(index);
            if (transaction.getOperationType() == getOperationType()) {
                String text = "PRODUCT: " + transaction.getProduct() + " || PRODUCTS SENDER: " +
                        transaction.getSender() + " || QUANTITY " + transaction.getQuantity();
                index++;
                return text;
            }
            index++;
            return null;
        }
    }
}
