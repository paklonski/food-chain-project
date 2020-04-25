package chainParticipants;

import product.*;
import visitor.*;
import channels.*;
import operations.*;

import java.util.*;

import static simulation.Main.LOGGER;

/**
 * The class describes the behavior of a farmer producing meat products.
 *
 * @author Artem Hurbych, Pavel Paklonski
 */
public class MeatFarmer extends Farmer {

    public MeatFarmer(String name, String country, int cashAccount, List<FoodEnum> ingredientList, TransactionInformer transactionInformer) {
        super(name, country, cashAccount, ingredientList, transactionInformer);
    }

    /**
     * The method registers a chain participant in each of the channels.
     */
    public void registerToTheChannel() {
        MeatChannel.getMeatChannel().register(this);
        PaymentChannel.getPaymentChannel().register(this);
    }

    public void accept(Visitor visitor) {
        visitor.doForMeatFarmer(this);
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
        FoodEntity ingredient = null;
        request.done = true;
        switch (ingredientName) {
            case MILK:
                ingredient = new FoodEntity(FoodEnum.MILK, quantity, 8);
                break;
            case MEAT:
                ingredient = new FoodEntity(FoodEnum.MEAT, quantity, 0);
                break;
            case BUTTER:
                ingredient = new FoodEntity(FoodEnum.BUTTER, quantity, -18);
                break;
            case EGGS:
                ingredient = new FoodEntity(FoodEnum.EGGS, quantity, 6);
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