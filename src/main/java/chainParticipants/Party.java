package chainParticipants;

import product.*;
import visitor.*;
import operations.*;

public interface Party {

    OperationEnum getOperationType();

    boolean isAgreeToExecute(Request request);

    FoodEntity process(Request request);

    void addFood(FoodEntity foodEntity);

    void registerToTheChannel();

    void updateTransactions(Transaction transaction);

    String getName();

    boolean isAgreeToPay(int price);

    void payMoney(int price);

    void getProfit();

    int askPrice(FoodEnum foodEnum, int quantity);

    int getCashAccount();

    int getLastHash();

    void accept(Visitor visitor);
}