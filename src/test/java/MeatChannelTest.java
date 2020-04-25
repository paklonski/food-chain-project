import org.junit.*;

import java.util.*;

import product.*;
import channels.*;
import chainParticipants.*;

import static product.FoodEnum.*;
import static org.junit.Assert.*;

public class MeatChannelTest {

    private Party customer;
    private Party meatFarmer;
    private Party manufacturer;
    private List<FoodEnum> meatIngredients = new ArrayList<>();
    private List<Party> participants = new ArrayList<>();

    @Before
    public void setUp() {
        Collections.addAll(meatIngredients, MILK, MEAT, BUTTER, EGGS);
        meatFarmer = new MeatFarmer("Tomas", "USA", 1000, meatIngredients, null);
        manufacturer = new Manufacturer("Tomas", 1000, BORSHCH, null);
        customer = new Customer("Julie", 1000, null);
        Collections.addAll(participants, meatFarmer, manufacturer, customer);
        MeatChannel.getMeatChannel(participants, meatIngredients, null);
        MeatChannel.getMeatChannel().register(meatFarmer);
        MeatChannel.getMeatChannel().register(manufacturer);
        MeatChannel.getMeatChannel().register(customer);
    }

    @Test
    public void register() {
        List<Party> expectedParticipants = new ArrayList<>();
        Collections.addAll(expectedParticipants, meatFarmer, manufacturer, customer);
        int expectedSize = 3;
        int actualSize = MeatChannel.getMeatChannel().getCurrentParticipants().size();
        assertEquals(expectedSize, actualSize);
    }
}