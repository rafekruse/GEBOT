package main.java.DataStructures;

public class BuyCooldown extends Item{

    private long nextAvailableTimeToBuy;

    public BuyCooldown(String name){
        super(name);
        nextAvailableTimeToBuy = System.currentTimeMillis();
    }

    public void setNewAvailableToBuy(){
        nextAvailableTimeToBuy = System.currentTimeMillis() + (4 * 60 * 60 * 1000);
    }
    
    public boolean offCooldown(){
        return nextAvailableTimeToBuy < System.currentTimeMillis();
    }

    public long getNextAvailableTimeToBuy() {
        return nextAvailableTimeToBuy;
    }
    
}