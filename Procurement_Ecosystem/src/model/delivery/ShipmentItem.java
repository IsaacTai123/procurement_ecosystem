/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import model.product.Product;

/**
 *
 * @author linweihong
 */
public class ShipmentItem {

    private Product product;
    private int quantity;

    public ShipmentItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        // todo: warning window: quantity should be at least 1
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    
    

}
