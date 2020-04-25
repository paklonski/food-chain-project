package chainParticipants;

import product.*;
import visitor.*;
import channels.*;
import operations.*;

import java.util.*;

import static simulation.Main.LOGGER;
import static operations.OperationEnum.*;

/**
 * The class describes the behavior of a retailer.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public class Retailer extends ChainParticipant {

    private List<FoodEntity> products = new ArrayList<>();
    private OperationEnum operationType = RETAIL;

    public Retailer(String name, int cashAccount, TransactionInformer transactionInformer) {
        super(name, cashAccount, transactionInformer);
    }

    public void accept(Visitor visitor) {
        visitor.doForRetailer(this);
    }

    public List<FoodEntity> getProducts() {
        return products;
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

    public OperationEnum getOperationType() {
        return operationType;
    }

    /**
     * Check if the retailer can execute the request.
     *
     * @param request the instance of Request
     * @return true - if the farmer can execute the request
     */
    public boolean isAgreeToExecute(Request request) {
        for (FoodEntity foodEntity : products) {
            if (foodEntity.getName() == request.getFoodEnum()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sends the product to the applicant, removing it from its own list of products.
     *
     * @param request the request
     * @return the ingredient
     */
    public FoodEntity process(Request request) {
        for (FoodEntity product : products) {
            if (request.getFoodEnum() == product.getName()) {
                products.remove(product);
                request.done = true;
                product.transport(request.getApplicant());
                return product;
            }
        }
        LOGGER.warning("Product wasn't taken from retailer " + getName());
        return null;
    }

    /**
     * Add the entity of food to the list of products.
     *
     * @param foodEntity the instance of food that was produced by someone
     */
    public void addFood(FoodEntity foodEntity) {
        foodEntity.addAction(foodEntity + " with name " + foodEntity.getName() + " come to " + this);
        products.add(foodEntity);
    }
}

