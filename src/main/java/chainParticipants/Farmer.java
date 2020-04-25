package chainParticipants;

import product.*;
import operations.*;

import java.util.*;

import static operations.OperationEnum.*;

/**
 * The class describes the general state and behavior of a farmer.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public abstract class Farmer extends ChainParticipant {

    private String country;
    private List<FoodEnum> ingredientList;
    private List<FoodEntity> products = new ArrayList<>();
    private OperationEnum operationType = GROW;

    public Farmer(String name, String country, int cashAccount, List<FoodEnum> ingredientList, TransactionInformer transactionInformer) {
        super(name, cashAccount, transactionInformer);
        this.country = country;
        this.ingredientList = ingredientList;
    }

    public OperationEnum getOperationType() {
        return operationType;
    }

    /**
     * Check if the farmer can execute the request.
     *
     * @param request the instance of Request
     * @return true - if the farmer can execute the request
     */
    public boolean isAgreeToExecute(Request request) {
        return ingredientList.contains(request.getFoodEnum());
    }

    public abstract FoodEntity process(Request request);

    /**
     * Add the entity of food to the list of products.
     *
     * @param foodEntity the instance of food that was produced by someone
     */
    public void addFood(FoodEntity foodEntity) {
        products.add(foodEntity);
    }
}
