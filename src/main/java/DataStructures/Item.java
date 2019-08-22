package main.java.DataStructures;

public abstract class Item{

    private String name;

    public Item(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean equals(Item one, Item two){
        return one.name == two.name;
    }
}