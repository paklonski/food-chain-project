package states;

import product.*;

public class FinalPackedState extends State {

    public FinalPackedState(FoodEntity foodEntity) {
        super(foodEntity);
    }

    public boolean cook() {
        System.out.println("Product " + foodEntity.getName() + " cannot be used for cooking until it is packed. It will be unpacked.");
        return false;
    }

    public boolean transport() {
        System.out.println("Product cannot be transported until it is not packed. It will be packed in temporary packaging.");
        return false;
    }

    public boolean presentProductToCustomer() {
        return true;
    }
}
