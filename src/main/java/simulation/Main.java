package simulation;

import product.*;
import visitor.*;
import channels.*;
import operations.*;
import chainParticipants.*;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static product.FoodEnum.*;

public class Main {

    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {

        List<Party> participants = new ArrayList<>();
        TransactionInformer transactionInformer = new TransactionInformer();
        List<FoodEnum> meatIngredients = Arrays.asList(MILK, MEAT, BUTTER, EGGS);
        List<FoodEnum> vegetableIngredients = Arrays.asList(POTATO, ONION, SUGAR, FLOUR, BEET, WATER);
        List<FoodEnum> readyMeals = Arrays.asList(PANCAKES, KYIVCUTLET, DRANIKI, ICECREAM, BORSHCH);

        // Create all participants
        MeatFarmer meatFarmer = new MeatFarmer("MEAT FARMER", "BELARUS", 2000,
                meatIngredients, transactionInformer);
        VegetableFarmer vegetableFarmer = new VegetableFarmer("VEGETABLE FARMER", "UKRAINE", 2000,
                vegetableIngredients, transactionInformer);
        Manufacturer manufacturerBorshch = new Manufacturer("BORSHCH MANUFACTURER", 1000,
                BORSHCH, transactionInformer);
        Manufacturer manufacturerDraniki = new Manufacturer("DRANIKI MANUFACTURER", 1000,
                DRANIKI, transactionInformer);
        Manufacturer manufacturerIceCream = new Manufacturer("ICE CREAM MANUFACTURER", 1000,
                ICECREAM, transactionInformer);
        Manufacturer manufacturerKyivCutlet = new Manufacturer("KYIVCUTLET MANUFACTURER", 1000,
                KYIVCUTLET, transactionInformer);
        Manufacturer manufacturerPancakes = new Manufacturer("PANCAKES MANUFACTURER", 1000,
                PANCAKES, transactionInformer);
        Storehouse storehouse = new Storehouse("STOREHOUSE", 1000, transactionInformer);
        Seller seller = new Seller("SELLER", 1000, transactionInformer);
        Retailer retailer = new Retailer("RETAILER", 1000, transactionInformer);
        Customer customer1 = new Customer("CUSTOMER-1", 400, transactionInformer);
        Customer customer2 = new Customer("CUSTOMER-2", 1000, transactionInformer);

        Collections.addAll(participants, meatFarmer, vegetableFarmer, manufacturerBorshch, manufacturerDraniki,
                manufacturerIceCream, manufacturerKyivCutlet, manufacturerPancakes,
                storehouse, seller, retailer, customer1, customer2);

        // Create channels
        MeatChannel.getMeatChannel(participants, meatIngredients, transactionInformer);
        VegetableChannel.getVegetableChannel(participants, vegetableIngredients, transactionInformer);
        ReadyMealChannel.getReadyMealChannel(participants, readyMeals, transactionInformer);
        PaymentChannel.getPaymentChannel(participants, transactionInformer);

        PriceWriter visitor = new PriceWriter();
        visitor.write(participants);

        // Register participants to the channel and attach them to the Transaction Informer
        for (Party participant : participants) {
            transactionInformer.attach(participant);
            participant.registerToTheChannel();
        }

        // STEP 1
        // Fill some participants with products
        manufacturerDraniki.sendRequest(POTATO, 2);
        storehouse.sendRequest(BORSHCH, 2);
        storehouse.sendRequest(MEAT, 1);
        storehouse.sendRequest(DRANIKI, 1);
        seller.sendRequest(KYIVCUTLET, 1);
        seller.sendRequest(POTATO, 2);
        retailer.sendRequest(BEET, 3);
        retailer.sendRequest(PANCAKES, 2);

        // STEP 2
        customer1.sendRequest(ICECREAM, 2);
        customer1.sendRequest(MILK, 3);
        customer2.sendRequest(DRANIKI, 1);
        customer2.sendRequest(PANCAKES, 2);
        storehouse.sendRequest(DRANIKI, 1);
        seller.sendRequest(POTATO, 2);
        retailer.sendRequest(KYIVCUTLET, 1);

        // STEP 3
        customer1.sendRequest(ICECREAM, 2);
        customer1.sendRequest(POTATO, 2);
        customer2.sendRequest(BORSHCH, 2);

        generateTextPartiesReport(storehouse);
        transactionInformer.makeReport();
        transactionInformer.makeSecurityReport();
        transactionInformer.generateTextReport();
    }

    private static void generatePartiesReport(ChainParticipant chainParticipant) {
        for (Iterator it = chainParticipant.getIterator(); it.hasNext(); ) {
            Object text = it.next();
            if (text != null) {
                System.out.println(text);
            }
        }
    }

    private static void generateTextPartiesReport(ChainParticipant chainParticipant) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("PartyReport.txt"), "utf-8"))) {
            writer.write("-------------------- " + chainParticipant.getName() + " REPORT --------------------\n");
            for (Iterator it = chainParticipant.getIterator(); it.hasNext(); ) {
                Object text = it.next();
                if (text != null) {
                    writer.write((String) text + "\n");
                }
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
