import org.junit.*;
import operations.*;
import chainParticipants.*;

import static product.FoodEnum.*;
import static org.junit.Assert.*;

public class RequestTest {

    private Customer customer;
    private Request request;

    @Before
    public void setUp() {
        customer = new Customer("John", 0, null);
        request = new Request(customer, MILK, 5, null);
    }

    @Test
    public void checkPrice() {
        int expectedPrice = 100;
        request.setPrice(100);
        int actualPrice = request.getPrice();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void getApplicant() {
        String expectedName = "John";
        String actualName = request.getApplicant().getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void getQuantity() {
        int expectedQuantity = 5;
        int actualQuantity = request.getQuantity();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testToString() {
        String expectedText = "REQUEST: " + "applicant = " + customer + ", foodEnum = " + MILK + ", quantity = " + 5 +
                ", operationType = " + null + '.';
        String actualText = request.toString();
        assertEquals(expectedText, actualText);
    }
}