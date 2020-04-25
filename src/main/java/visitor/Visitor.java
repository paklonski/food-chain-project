package visitor;

import chainParticipants.*;

public interface Visitor {

    void doForVegetableFarmer(VegetableFarmer party);

    void doForMeatFarmer(MeatFarmer party);

    void doForManufacturer(Manufacturer party);

    void doForStorehouse(Storehouse party);

    void doForSeller(Seller party);

    void doForRetailer(Retailer party);
}
