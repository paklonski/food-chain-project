package states;

import product.*;

public class UnpackedState extends State {

    public UnpackedState(FoodEntity foodEntity) {
        super(foodEntity);
    }

    public boolean cook() {
        return true;
    }

    public boolean transport() {
        System.out.println("Product " + foodEntity.getName() + " cannot be transported until it is not packed. It will be packed in transport packaging.");
        return false;
    }

    public boolean presentProductToCustomer() {
        System.out.println("Product " + foodEntity.getName() + " cannot be presented to customer until it is not finally packed. It will be packed in final packaging.");
        return false;
    }
}
