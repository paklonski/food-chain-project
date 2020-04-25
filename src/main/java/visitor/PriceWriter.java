package visitor;

import java.util.*;

import chainParticipants.*;

import static product.FoodEnum.*;

public class PriceWriter implements Visitor {

    public void write(List<Party> parties) {
        for (Party party : parties) {
            party.accept(this);
        }
    }

    public void doForVegetableFarmer(VegetableFarmer party) {
        party.put(POTATO, 18);
        party.put(ONION, 10);
        party.put(SUGAR, 20);
        party.put(FLOUR, 25);
        party.put(BEET, 15);
        party.put(WATER, 12);
    }

    public void doForMeatFarmer(MeatFarmer party) {
        party.put(MILK, 20);
        party.put(MEAT, 50);
        party.put(BUTTER, 25);
        party.put(EGGS, 30);
    }

    public void doForManufacturer(Manufacturer party) {
        party.put(BORSHCH, 150);
        party.put(PANCAKES, 160);
        party.put(KYIVCUTLET, 220);
        party.put(ICECREAM, 100);
        party.put(DRANIKI, 140);
    }

    public void doForStorehouse(Storehouse party) {
        party.put(BORSHCH, 200);
        party.put(PANCAKES, 220);
        party.put(KYIVCUTLET, 280);
        party.put(ICECREAM, 120);
        party.put(DRANIKI, 150);
        party.put(POTATO, 24);
        party.put(ONION, 12);
        party.put(SUGAR, 28);
        party.put(FLOUR, 32);
        party.put(BEET, 18);
        party.put(WATER, 14);
        party.put(MILK, 25);
        party.put(MEAT, 60);
        party.put(BUTTER, 30);
        party.put(EGGS, 40);
    }

    public void doForSeller(Seller party) {
        party.put(BORSHCH, 220);
        party.put(PANCAKES, 240);
        party.put(KYIVCUTLET, 300);
        party.put(ICECREAM, 140);
        party.put(DRANIKI, 170);
        party.put(POTATO, 30);
        party.put(ONION, 20);
        party.put(SUGAR, 34);
        party.put(FLOUR, 42);
        party.put(BEET, 25);
        party.put(WATER, 18);
        party.put(MILK, 35);
        party.put(MEAT, 68);
        party.put(BUTTER, 40);
        party.put(EGGS, 50);
    }

    public void doForRetailer(Retailer party) {
        party.put(BORSHCH, 250);
        party.put(PANCAKES, 280);
        party.put(KYIVCUTLET, 320);
        party.put(ICECREAM, 160);
        party.put(DRANIKI, 180);
        party.put(POTATO, 35);
        party.put(ONION, 25);
        party.put(SUGAR, 42);
        party.put(FLOUR, 48);
        party.put(BEET, 32);
        party.put(WATER, 22);
        party.put(MILK, 40);
        party.put(MEAT, 73);
        party.put(BUTTER, 50);
        party.put(EGGS, 55);
    }
}
