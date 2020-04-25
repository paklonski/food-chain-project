package chainParticipants;

import product.*;
import visitor.*;
import channels.*;
import operations.*;

import java.util.*;

import static simulation.Main.LOGGER;
import static operations.OperationEnum.*;

/**
 * The class describes the behavior of a warehouse with three types of refrigerators.
 * Products supplied from farmers and manufactures are stored for a certain amount of time,
 * after which they are passed on to the chain or participant who made a direct request
 * for a particular type of product.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public class Storehouse extends ChainParticipant {

    private OperationEnum operationType = STORE;
    private List<FoodEntity> coldFridge = new ArrayList<>();      // from -20 to -11
    private List<FoodEntity> mediumFridge = new ArrayList<>();    // from -10 to -1
    private List<FoodEntity> warmFridge = new ArrayList<>();      // from 0 to 10

    public Storehouse(String name, int cashAccount, TransactionInformer transactionInformer) {
        super(name, cashAccount, transactionInformer);
    }

    public void accept(Visitor visitor) {
        visitor.doForStorehouse(this);
    }

    public List<FoodEntity> getProducts() {
        List<FoodEntity> products = new ArrayList<>(coldFridge);
        products.addAll(mediumFridge);
        products.addAll(warmFridge);
        return products;
    }

    public OperationEnum getOperationType() {
        return operationType;
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

    private boolean check(FoodEnum foodEnum) {
        for (FoodEntity foodEntity : coldFridge) {
            if (foodEntity.getName() == foodEnum) {
                return true;
            }
        }
        for (FoodEntity foodEntity : mediumFridge) {
            if (foodEntity.getName() == foodEnum) {
                return true;
            }
        }
        for (FoodEntity foodEntity : warmFridge) {
            if (foodEntity.getName() == foodEnum) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the storehouse can execute the request.
     *
     * @param request the instance of Request
     * @return true - if the farmer can execute the request
     */
    public boolean isAgreeToExecute(Request request) {
        return check(request.getFoodEnum());
    }

    /**
     * Add the entity of food to the list of products.
     *
     * @param foodEntity the instance of food that was produced by someone
     */
    public void addFood(FoodEntity foodEntity) {
        int foodEntityTemperature = foodEntity.getStorageTemperature();
        if (foodEntityTemperature >= -20 && foodEntityTemperature <= -11) {
            coldFridge.add(foodEntity);
            foodEntity.addAction(foodEntity + " with name " + foodEntity.getName() + " was put to cold fridge in " + this);
        } else if (foodEntityTemperature >= -10 && foodEntityTemperature <= -1) {
            mediumFridge.add(foodEntity);
            foodEntity.addAction(foodEntity + " with name " + foodEntity.getName() + " was put to medium fridge in " + this);
        } else if (foodEntityTemperature >= 0 && foodEntityTemperature <= 10) {
            warmFridge.add(foodEntity);
            foodEntity.addAction(foodEntity + " with name " + foodEntity.getName() + " was put to warm fridge in " + this);
        } else {
            LOGGER.warning("Product came to storehouse " + getName() + " but wasn't added to any fridge.");
        }
    }

    /**
     * Sends the product to the applicant, removing it from its own list of products.
     *
     * @param request the request
     * @return the ingredient
     */
    public FoodEntity process(Request request) {

        for (FoodEntity foodEntity : coldFridge) {
            if (foodEntity.getName().equals(request.getFoodEnum())) {
                coldFridge.remove(foodEntity);
                request.done = true;
                foodEntity.transport(request.getApplicant());
                return foodEntity;
            }
        }

        for (FoodEntity foodEntity : mediumFridge) {
            if (foodEntity.getName().equals(request.getFoodEnum())) {
                mediumFridge.remove(foodEntity);
                request.done = true;
                foodEntity.transport(request.getApplicant());
                return foodEntity;
            }
        }

        for (FoodEntity foodEntity : warmFridge) {
            if (foodEntity.getName().equals(request.getFoodEnum())) {
                warmFridge.remove(foodEntity);
                request.done = true;
                foodEntity.transport(request.getApplicant());
                return foodEntity;
            }
        }
        LOGGER.warning("Product wasn't taken from storehouse " + getName());
        return null;
    }
}