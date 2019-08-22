package main.java.DataStructures;

public class TradingItem extends Item{

    private int buyPrice;
    private int sellPrice;
    private int quantity;

    public TradingItem(String name, int quantity, int buyPrice, int sellPrice){
        super(name);
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getBuyPrice() {
        return buyPrice;
    }
    public int getSellPrice() {
        return sellPrice;
    }

    public String toString() {
        return String.format("%-30s%-15s%-15s%-15s", getName(), buyPrice, sellPrice, quantity);
    }
}