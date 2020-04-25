package strategy;

import java.util.*;

import product.*;
import chainParticipants.*;

import static product.FoodEnum.*;
import static simulation.Main.LOGGER;

public class CookIceCream implements ManufacturerStrategy {

    private Manufacturer manufacturer;
    private HashMap<FoodEnum, FoodEntity> receipt = new HashMap<>() {{
        put(MILK, null);
        put(SUGAR, null);
    }};

    public CookIceCream(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    private boolean isReadyForCooking() {
        for (FoodEnum receiptItem : receipt.keySet()) {
            if (receipt.get(receiptItem) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cooking ice cream from ingredients.
     *
     * @return ready product
     */
    public FoodEntity cook() {

        Set<FoodEnum> receipt = this.receipt.keySet();

        for (FoodEnum receiptItem : receipt) {
            manufacturer.sendRequest(receiptItem, 1);
        }

        for (FoodEnum receiptItem : receipt) {
            for (int i = 0; i < manufacturer.getProducts().size(); i++) {
                FoodEnum productName = manufacturer.getProducts().get(i).getName();
                if (productName.equals(receiptItem) && this.receipt.get(receiptItem) == null) {
                    this.receipt.replace(receiptItem, manufacturer.getProducts().get(i));
                    manufacturer.getProducts().get(i).addAction(manufacturer.getProducts().get(i) + " with name " +
                            manufacturer.getProducts().get(i).getName() + " was used for cooking meal by " + this);
                    manufacturer.getProducts().get(i).useForCooking();
                    manufacturer.getProducts().remove(i);
                }
            }
        }

        if (isReadyForCooking()) {
            for (FoodEnum receiptItem : receipt) {
                this.receipt.replace(receiptItem, null);
            }
            FoodEntity meal = new FoodEntity(ICECREAM, 1, -5);
            meal.addAction(meal + " with name " + ICECREAM.toString() + " was created by " + this);
            return meal;
        }
        LOGGER.warning("PRODUCT ICE CREAM WAS NOT CREATED BY " + manufacturer.getName());
        return null;
    }
}

