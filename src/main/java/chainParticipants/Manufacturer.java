package chainParticipants;

import product.*;
import visitor.*;
import strategy.*;
import channels.*;
import operations.*;

import java.util.*;

import static simulation.Main.LOGGER;
import static operations.OperationEnum.*;

/**
 * The class describes the behavior of a manufacturer capable of creating certain types
 * of products based on requests and the availability of available ingredients.
 * If one or another ingredient is missing,
 * the manufacturer makes a request for this type of ingredient to the farmer.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public class Manufacturer extends ChainParticipant {

    private FoodEnum specialization;
    private ManufacturerStrategy strategy;
    private OperationEnum operationType = PRODUCE;
    private List<FoodEntity> products = new ArrayList<>();

    public Manufacturer(String name, int cashAccount, FoodEnum specialization, TransactionInformer transactionInformer) {
        super(name, cashAccount, transactionInformer);
        this.specialization = specialization;
    }

    public List<FoodEntity> getProducts() {
        return products;
    }

    public void accept(Visitor visitor) {
        visitor.doForManufacturer(this);
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

    @Override
    public OperationEnum getOperationType() {
        return operationType;
    }

    /**
     * The method returns 'true' if the object of the request is included in its specialization.
     *
     * @param request the request
     * @return true if the manufacturer can execute the request
     */
    public boolean isAgreeToExecute(Request request) {
        return request.getFoodEnum().equals(this.specialization);
    }

    private void setStrategy(FoodEnum expectedProduct) {
        switch (expectedProduct) {
            case BORSHCH:
                this.strategy = new CookBorshch(this);
                break;
            case PANCAKES:
                this.strategy = new CookPancakes(this);
                break;
            case KYIVCUTLET:
                this.strategy = new CookKyivCutlet(this);
                break;
            case ICECREAM:
                this.strategy = new CookIceCream(this);
                break;
            case DRANIKI:
                this.strategy = new CookDraniki(this);
                break;
            default:
                break;
        }
    }

    /**
     * Starts the product manufacturing process.
     *
     * @param request the request
     * @return the product
     */
    public FoodEntity process(Request request) {
        setStrategy(request.getFoodEnum());
        request.done = true;
        FoodEntity product = strategy.cook();
        if (product != null) {
            product.transport(request.getApplicant());
        } else {
            LOGGER.warning("Ingredient was not created by Manufacturer " + getName());
        }
        return product;
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