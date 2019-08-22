package main.java.DataStructures;

public class InventoryItem extends Item{

    private int inventoryQuantity;
    private int sellPrice;

    public InventoryItem(String name){
        super(name);
        this.inventoryQuantity = 0;
    }
    //When restarting the sell price might not be valid would have to be rechecked
    public InventoryItem(ItemExchangeInfo item, int quantity){
        super(item.getName());
        inventoryQuantity = quantity;
        sellPrice = item.getBuy_average() - 1;
    }

    public InventoryItem(String name, int inventoryQuantity, int sellPrice){
        super(name);
        this.inventoryQuantity = inventoryQuantity;
        this.sellPrice = sellPrice;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }
    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }
    public void addInventoryQuantity(int inventoryQuantity){
        this.inventoryQuantity += inventoryQuantity;
    }

}