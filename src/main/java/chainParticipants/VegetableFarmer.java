package chainParticipants;

import product.*;
import visitor.*;
import channels.*;
import operations.*;

import java.util.List;

import static simulation.Main.LOGGER;

/**
 * The class describes the behavior of a farmer producing vegetable products.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public class VegetableFarmer extends Farmer {

    public VegetableFarmer(String name, String country, int cashAccount, List<FoodEnum> ingredientList, TransactionInformer transactionInformer) {
        super(name, country, cashAccount, ingredientList, transactionInformer);
    }

    /**
     * The method registers a chain participant in each of the channels.
     */
    public void registerToTheChannel() {
        VegetableChannel.getVegetableChannel().register(this);
        PaymentChannel.getPaymentChannel().register(this);
    }

    public void accept(Visitor visitor) {
        visitor.doForVegetableFarmer(this);
    }

    /**
     * Starts the product manufacturing process.
     *
     * @param request the request
     * @return the ingredient
     */
    public FoodEntity process(Request request) {

        FoodEnum ingredientName = request.getFoodEnum();
        int quantity = request.getQuantity();
        request.done = true;
        FoodEntity ingredient = null;
        switch (ingredientName) {
            case POTATO:
                ingredient = new FoodEntity(FoodEnum.POTATO, quantity, 10);
                break;
            case ONION:
                ingredient = new FoodEntity(FoodEnum.ONION, quantity, 8);
                break;
            case BEET:
                ingredient = new FoodEntity(FoodEnum.BEET, quantity, 8);
                break;
            case SUGAR:
                ingredient = new FoodEntity(FoodEnum.SUGAR, quantity, 8);
                break;
            case FLOUR:
                ingredient = new FoodEntity(FoodEnum.FLOUR, quantity, 8);
                break;
            case WATER:
                ingredient = new FoodEntity(FoodEnum.WATER, quantity, 10);
                break;
        }
        if (ingredient == null) {
            LOGGER.warning("Ingredient was not created by Farmer " + getName());
        } else {
            ingredient.addAction(ingredient + " with name " + ingredientName.toString() + " was created by " + this);
            ingredient.transport(request.getApplicant());
        }
        return ingredient;
    }
}
