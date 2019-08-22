package main.java.DataStructures;

public class PurchasableItem extends Item {

    private int buyPrice;
    private int sellPrice;
    private int margin;
    private int buyLimit;

    public PurchasableItem(ItemExchangeInfo info) {
        super(info.getName());

        buyLimit = info.getBuyLimit();
        margin = info.getBuy_average() - info.getSell_average();
        buyPrice = info.getBuy_average();
        sellPrice = info.getSell_average();

    }

    public String toString() {
        return String.format("%-30s%-15s%-15s%-15s%-15s", getName(), buyPrice, sellPrice, margin, buyLimit);
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public int getMargin() {
        return margin;
    }


    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}