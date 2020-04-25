package operations;

import product.*;
import chainParticipants.*;

public class Request {

    private Party applicant;
    private FoodEnum foodEnum;
    private int price;
    private int quantity;
    private OperationEnum operationType;
    public boolean done = false;

    public Request(Party applicant, FoodEnum foodEnum, int quantity, OperationEnum operationType) {
        this.applicant = applicant;
        this.foodEnum = foodEnum;
        this.quantity = quantity;
        this.operationType = operationType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Party getApplicant() {
        return applicant;
    }

    public FoodEnum getFoodEnum() {
        return foodEnum;
    }

    public int getQuantity() {
        return quantity;
    }

    public OperationEnum getOperationType() {
        return operationType;
    }

    @Override
    public String toString() {
        return "REQUEST: " + "applicant = " + applicant +
                ", foodEnum = " + foodEnum + ", quantity = " + quantity +
                ", operationType = " + operationType + '.';
    }
}
