import org.junit.*;

import java.util.*;

import product.*;
import visitor.*;
import operations.*;
import chainParticipants.*;

import static product.FoodEnum.*;
import static org.junit.Assert.*;

public class VegetableFarmerTest {

    private Farmer farmer;
    private Request request;

    @Before
    public void setUp() {
        farmer = new VegetableFarmer(null, null, 0,
                Arrays.asList(POTATO, ONION, SUGAR, FLOUR, BEET, WATER), null);
        request = new Request(null, ONION, 1, OperationEnum.GROW);
    }

    @Test
    public void checkAskPrice() {
        int expectedPrice = 40;
        farmer.accept(new PriceWriter());
        int actualPrice = farmer.askPrice(ONION, 4);
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void checkIsAgreeToExecute() {
        boolean is = farmer.isAgreeToExecute(request);
        assert (is);
    }

    @Test
    public void checkProcess1() {
        FoodEnum expectedName = ONION;
        FoodEntity product = farmer.process(request);
        FoodEnum actualName = product.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void checkProcess2() {
        int expectedQuantity = 1;
        FoodEntity product = farmer.process(request);
        int actualQuantity = product.getQuantity();
        assertEquals(expectedQuantity, actualQuantity);
    }
}