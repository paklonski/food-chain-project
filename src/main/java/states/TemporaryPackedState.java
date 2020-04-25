package states;

import product.*;

public class TemporaryPackedState extends State {

    public TemporaryPackedState(FoodEntity foodEntity) {
        super(foodEntity);
    }

    public boolean cook() {
        System.out.println("Product " + foodEntity.getName() + " cannot be used for cooking until it is packed. It will be unpacked.");
        return false;
    }

    public boolean transport() {
        return true;
    }

    public boolean presentProductToCustomer() {
        System.out.println("Product " + foodEntity.getName() + " cannot be presented to customer until it is not finally packed. It will be packed in final packaging.");
        return false;
    }
}
