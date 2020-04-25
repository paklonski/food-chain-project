package chainParticipants;

import product.*;
import visitor.*;
import channels.*;
import operations.*;

import java.util.*;

import static operations.OperationEnum.*;

/**
 * The class describes the state and behavior of the buyer.
 * The buyer can send requests, but can’t take action.
 * It is the last, sixth, participant in the chain of participants.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public class Customer extends ChainParticipant {

    private OperationEnum operationType = CUSTOMER;
    private List<FoodEntity> products = new ArrayList<>();

    public Customer(String name, int cashAccount, TransactionInformer transactionInformer) {
        super(name, cashAccount, transactionInformer);
    }

    public void accept(Visitor visitor) {
        //customer don't sell anything so he doesn't have any prices
    }

    /**
     * The method registers a chain participant in each of the channels.
     */
    public void registerToTheChannel() {
        MeatChannel.getMeatChannel().register(this);
        VegetableChannel.getVegetableChannel().register(this);
        ReadyMealChannel.getReadyMealChannel().register(this);
        PaymentChannel.getPaymentChannel().register(this);
    }

    public List<FoodEntity> getProducts() {
        return products;
    }

    /**
     * The buyer cannot sell anything.
     *
     * @param foodEnum the type of a product
     * @param quantity the quantity of the product
     * @return the price of the product
     */
    public int askPrice(FoodEnum foodEnum, int quantity) {
        return 0;
    }

    public OperationEnum getOperationType() {
        return operationType;
    }

    public boolean isAgreeToExecute(Request request) {
        return false;
    }

    public FoodEntity process(Request request) {
        return null;
    }

    /**
     * The method adds the product to the list of available products at the chain member.
     *
     * @param foodEntity the type of a product
     */
    public void addFood(FoodEntity foodEntity) {
        foodEntity.addAction(foodEntity + " with name " + foodEntity.getName() + " come to " + this);
        products.add(foodEntity);
    }
}
