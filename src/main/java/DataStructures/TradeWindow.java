package main.java.DataStructures;

public class TradeWindow {
    private int windowNumber;
    private WindowState state;
    private TradingItem currentItem;
    private int pricePerItem;
    private int totalPrice;
    private int maxTradeAmount;
    private int alreadyTraded;
    private int collected;
    private long lastTraded;

    public TradeWindow(int windowNum) {
        windowNumber = windowNum;
        state = WindowState.Empty;
        currentItem = null;
        pricePerItem = 0;
        totalPrice = 0;
        alreadyTraded = 0;
        maxTradeAmount = 0;
        lastTraded = 0;
        collected = 0;
    }
    public int getWindowNumber(){return  windowNumber;}

    public int getMaxTradeAmount() {
        return maxTradeAmount;
    }

    public WindowState getState() {
        return state;
    }

    public int getAlreadyTraded() {
        return alreadyTraded;
    }

    public int getCollected() {
        return collected;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public TradingItem getItem() {
        return currentItem;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public long getLastTraded() {
        return lastTraded;
    }

    public void setState(WindowState state){
        this.state = state;
    }



    public void SetBuyData(TradingItem item) {
        this.currentItem = item;
        pricePerItem = item.getBuyPrice();
        totalPrice = item.getQuantity() + item.getBuyPrice();
        maxTradeAmount = item.getQuantity();
        state = WindowState.Buy;
        lastTraded = System.currentTimeMillis();
    }

    public void SetSellData(TradingItem item) {
        this.currentItem = item;
        pricePerItem = item.getSellPrice();
        totalPrice = item.getQuantity() + item.getSellPrice();
        maxTradeAmount = item.getQuantity();
        state = WindowState.Sell;
        lastTraded = System.currentTimeMillis();
    }



    public void updateTraded(int amountTraded) {
        alreadyTraded = amountTraded;
        lastTraded = System.currentTimeMillis();
        if (alreadyTraded == maxTradeAmount) {
            state = WindowState.Complete;
        }
    }



}

;