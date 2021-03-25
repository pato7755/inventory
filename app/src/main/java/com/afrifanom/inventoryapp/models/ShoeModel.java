package com.afrifanom.inventoryapp.models;

public class ShoeModel extends ShoesResponseModel{

    private String id;

    private String brand;

    private String size;

    private String price;

    private String color;

    public ShoeModel() {
    }

    public ShoeModel(String brand, String size, String color, String price) {
        this.brand = brand;
        this.size = size;
        this.price = price;
        this.color = color;
    }

    public ShoeModel(String id, String brand, String size, String color, String price) {
        this.id = id;
        this.brand = brand;
        this.size = size;
        this.price = price;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                " brand='" + brand + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
