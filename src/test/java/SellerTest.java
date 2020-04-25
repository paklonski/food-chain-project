import org.junit.*;
import product.*;
import visitor.*;
import operations.*;
import chainParticipants.*;

import static org.junit.Assert.*;

public class SellerTest {

    private Seller seller;
    private Request request;

    @Before
    public void setUp() {
        seller = new Seller("seller", 500, null);
        request = new Request(null, FoodEnum.MILK, 1, OperationEnum.SELL);
    }

    @Test
    public void checkAskPrice() {
        int expectedPrice = 3000;
        seller.accept(new PriceWriter());
        int actualPrice = seller.askPrice(FoodEnum.KYIVCUTLET, 10);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void checkIsAgreeToExecute() {
        seller.addFood(new FoodEntity(FoodEnum.MILK, 1, 5));
        boolean is = seller.isAgreeToExecute(request);
        assert (is);
    }

    @Test
    public void checkProcess() {
        FoodEntity expectedProduct = new FoodEntity(FoodEnum.MILK, 1, 5);
        seller.addFood(new FoodEntity(FoodEnum.MILK, 1, 5));
        FoodEntity actualProduct = seller.process(request);
        assertEquals(expectedProduct.getName(), actualProduct.getName());
    }
}