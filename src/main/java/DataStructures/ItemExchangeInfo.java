package main.java.DataStructures;

import java.awt.image.BufferedImage;

public class ItemExchangeInfo {
    private int id = 0;
    private String name = "";
    private boolean members = false;
    private int sp = 0;
    private int buyLimit = 0;

    private int buy_average = 0;
    private int buy_quantity = 0;

    private int sell_average = 0;
    private int sell_quantity = 0;

    private int overall_average = 0;
    private int overall_quantity = 0;

    private BufferedImage sprite;

    public ItemExchangeInfo(){}

    //<editor-fold desc="Getters and Setters">
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean getMembers(){
        return members;
    }
    public void setMembers(boolean members) {
        this.members = members;
    }
    public int getSp() {
        return sp;
    }
    public void setSp(int sp) {
        this.sp = sp;
    }
    public int getBuyLimit() {
        return buyLimit;
    }
    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }
    public int getBuy_average() {
        return buy_average;
    }
    public void setBuy_average(int buy_average) {
        this.buy_average = buy_average;
    }
    public int getBuy_quantity() {
        return buy_quantity;
    }
    public void setBuy_quantity(int buy_quantity) {
        this.buy_quantity = buy_quantity;
    }
    public int getSell_average() {
        return sell_average;
    }
    public void setSell_average(int sell_average) {
        this.sell_average = sell_average;
    }
    public int getSell_quantity() {
        return sell_quantity;
    }
    public void setSell_quantity(int sell_quantity) {
        this.sell_quantity = sell_quantity;
    }
    public int getOverall_average() {
        return overall_average;
    }
    public void setOverall_average(int overall_average) {
        this.overall_average = overall_average;
    }
    public int getOverall_quantity() {
        return overall_quantity;
    }
    public void setOverall_quantity(int overall_quantity) {
        this.overall_quantity = overall_quantity;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
    //</editor-fold>

    public boolean isViable(int cashStack, int minimumProfit){
        if(!LacksBuyLimit() && !LacksPricesAverage() && 
           !TradedFewTimes(minimumProfit) && !PricePerItemExceedsValue(cashStack / 3) && !ProfitLessThanRequired(cashStack, minimumProfit)){
               return true;
        }
        return false;
    }


    private boolean LacksBuyLimit() {
        return buyLimit < 1;
    }
    private boolean LacksPricesAverage() {
        return buy_average < 1 || sell_average < 1;
    }
    private boolean TradedFewTimes(int minimumProfit) {
        return overall_quantity * (buy_average - sell_average) * 4 < minimumProfit;
    }
    private boolean PricePerItemExceedsValue(int price) {
        return overall_average > price;
    }
    private boolean ProfitLessThanRequired(int totalGold, int minimumProfit){
        int buyableQuantity = totalGold / sell_average > buyLimit ? buyLimit : totalGold / sell_average;
        return (buy_average - sell_average) * buyableQuantity < minimumProfit;
    }

}
