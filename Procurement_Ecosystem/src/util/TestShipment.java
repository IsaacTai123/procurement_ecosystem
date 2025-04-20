/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import directory.PurchaseOrderDirectory;
import java.util.ArrayList;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseOrder;
import model.product.Product;
import model.product.Spec;
import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public class TestShipment {
   
    
    
    public void sendPOToVendor(UserAccount buyerAccount, UserAccount vendorAccount){
        
        
        
        PurchaseOrder po = new PurchaseOrder("Quotation-001", buyerAccount, vendorAccount, createPurchaseItems(), "134 chelsea st", 0, "I have gived you purchase orders.");
        PurchaseOrderDirectory purchaseOrderList = new PurchaseOrderDirectory();
        purchaseOrderList.addPurchaseOrder(po);
        
        Enterprise vendorEnterprise = vendorAccount.getEnterprise(); // every shipment coordinator can see his company's all PO
        vendorEnterprise.setPurchaseOrderList(purchaseOrderList);
    
    
    }
    
    public ArrayList<PurchaseItem> createPurchaseItems(){
        
        ArrayList<PurchaseItem> purchaseItems = new ArrayList<>();
        
        
        // Product 1
        Product p1 = new Product("Dell Monitor");
        Spec s1 = new Spec("U2723QE", "Black", "27-inch", "Plastic/Metal", "Monitor", "4K, USB-C");
        purchaseItems.add(new PurchaseItem(p1, 10, 279.99, s1));

        // Product 2
        Product p2 = new Product("Logitech MX Master 3");
        Spec s2 = new Spec("MXM3", "Graphite", "Standard", "Plastic", "Mouse", "Ergonomic, Wireless");
        purchaseItems.add(new PurchaseItem(p2, 25, 89.99, s2));

        // Product 3
        Product p3 = new Product("ThinkPad X1 Carbon Gen 11");
        Spec s3 = new Spec("X1C11", "Black", "14-inch", "Carbon Fiber", "Laptop", "i7, 16GB, 512GB SSD");
        purchaseItems.add(new PurchaseItem(p3, 5, 1799.00, s3));

        // Product 4
        Product p4 = new Product("Cisco Catalyst 1000");
        Spec s4 = new Spec("C1000-24P", "Silver", "1U", "Metal", "Network Switch", "24-port PoE, Gigabit");
        purchaseItems.add(new PurchaseItem(p4, 3, 649.00, s4));

        // Product 5
        Product p5 = new Product("Apple Magic Keyboard");
        Spec s5 = new Spec("MK2A3LL/A", "White", "Compact", "Aluminum", "Keyboard", "Bluetooth, US layout");
        purchaseItems.add(new PurchaseItem(p5, 15, 99.00, s5));
        
        return purchaseItems;
    }
    
    
    
}
