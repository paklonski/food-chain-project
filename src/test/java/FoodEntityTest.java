import org.junit.*;

import java.util.*;

import states.*;
import product.*;
import operations.*;
import chainParticipants.*;

import static product.FoodEnum.*;
import static org.junit.Assert.*;

public class FoodEntityTest {

    private List<String> doneActions;
    private FoodEntity foodEntity;

    @Before
    public void setUp() {
        foodEntity = new FoodEntity(POTATO, 1, 1);
        doneActions = new ArrayList<>();
        doneActions.add("Action 1");
    }

    @Test
    public void checkAddAction() {
        List<String> expectedActions = new ArrayList<>();
        Collections.addAll(expectedActions, "Action 1", "Action 2");
        foodEntity.addAction("Action 1");
        foodEntity.addAction("Action 2");
        assertEquals(expectedActions, foodEntity.getDoneActions());
    }

    @Test
    public void checkTransport() {
        State expectedState = new TemporaryPackedState(this.foodEntity);
        foodEntity.transport(new Customer("Lukas", 100, new TransactionInformer()));
        State actualState = foodEntity.getState();
        assertNotEquals(expectedState, actualState);
    }
}