/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

import util.ProductUtil;

/**
 * This class represents the actual products that vendors have.
 * @author tisaac
 */
public class Product {
    private String id;
    private String name;

    public Product(String name) {
        this.name = name;
        this.id = ProductUtil.generateProductId(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

}
