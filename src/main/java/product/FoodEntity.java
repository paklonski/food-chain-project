package product;

import states.*;
import chainParticipants.*;

import java.io.*;
import java.util.*;

import static simulation.Main.LOGGER;

public class FoodEntity {

    private FoodEnum name;
    private int quantity;
    private final int storageTemperature;
    private List<String> doneActions = new ArrayList<>();
    private State state;

    public FoodEntity(FoodEnum name, int quantity, int storageTemperature) {
        this.name = name;
        this.quantity = quantity;
        this.storageTemperature = storageTemperature;
        this.state = new UnpackedState(this);
    }

    public FoodEnum getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStorageTemperature() {
        return storageTemperature;
    }

    public List<String> getDoneActions() {
        return doneActions;
    }

    public void addAction(String action) {
        doneActions.add(action);
    }

    /**
     * Uses for product report.
     */
    public void writeAllActions() {
        for (String action : doneActions) {
            System.out.println(action);
        }
    }

    public void writeAllActionsText() {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this + "Report.txt"), "utf-8"))) {
            writer.write("-------------------- FOODENTITY INFORMER REPORT --------------------\n");
            for (String action : doneActions) {
                writer.write(action + "\n");
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Uses for change food entity state to unpacked.
     */
    public void useForCooking() {
        if (!state.cook()) state = new UnpackedState(this);
    }

    /**
     * If applicant is customer, food entity will be packed in final packing, otherwise in temporary packing.
     *
     * @param apllicant
     */
    public void transport(Party apllicant) {
        if (apllicant instanceof Customer) {
            if (!state.presentProductToCustomer()) {
                state = new FinalPackedState(this);
            }
        } else if (!state.transport()) {
            state = new TemporaryPackedState(this);
        }
    }

    public State getState() {
        return state;
    }
}
