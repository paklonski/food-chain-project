package states;

import product.*;

public abstract class State {

    FoodEntity foodEntity;

    State(FoodEntity foodEntity) {
        this.foodEntity = foodEntity;
    }

    /**
     * Uses for check if food entity may be used for cooking.
     *
     * @return true if product unpacked,false otherwise
     */
    public abstract boolean cook();

    /**
     * Uses for check if food entity may be transported.
     *
     * @return true if product temporary packed, false otherwise
     */
    public abstract boolean transport();

    /**
     * Uses for check if food entity may be presented to customer.
     *
     * @return true if product final packed,false otherwise
     */
    public abstract boolean presentProductToCustomer();
}
