package edu.school21.reflection.asset;

public class Car {
    private String model;
    private String color;
    private int creationYear;
    private int price;
    private boolean forSale;

    public Car(String model, String color, int creationYear, int price, boolean forSale) {
        this.model = model;
        this.color = color;
        this.creationYear = creationYear;
        this.price = price;
        this.forSale = forSale;
    }

    public Car() {
    }

    public int increaseCreationYear(int value) {
        this.creationYear += value;
        return creationYear;
    }

    public int increasePrice(int value) {
        this.price += value;
        return price;
    }

    public int decreasePrice(int value) {
        this.price -= value;
        return price;
    }

    public void forSale(boolean forSale) {
        this.forSale = forSale;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", creationYear=" + creationYear +
                ", price=" + price +
                ", forSale=" + forSale +
                '}';
    }
}