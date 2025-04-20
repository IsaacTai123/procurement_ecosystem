package directory;

import enums.EnterpriseType;
import model.ecosystem.Enterprise;

import java.util.ArrayList;
import java.util.List;
import model.delivery.ShipmentDirectory;

/**
 * @author tisaac
 */
public class ShipmentDirectories {

    private final ArrayList<ShipmentDirectory> shipmentDirectories; // final: immutable list, so there's only one
                                                                    // shipmentDirectories in a network

    public ShipmentDirectories() {
        this.shipmentDirectories = new ArrayList<>();
    }

    // get an enterprise's shipment directory
    public ShipmentDirectory getShipmentDirectory(Enterprise enterprise) {

        for (ShipmentDirectory shipmentDirectory : shipmentDirectories) {
            if (shipmentDirectory.getEnterprise() == enterprise) {
                System.out.println("directory.ShipmentDirectories.getShipmentDirectory()");
                return shipmentDirectory;
            }

        }

        return null;

    }

    public void addShipmentDirectory(ShipmentDirectory shipmentDirectory) {
        shipmentDirectories.add(shipmentDirectory);

    }

}
