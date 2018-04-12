package VendingMachineTestDrivenPackage;

public class Item {

    private String name;
    private double price;
    private int amount;

    public Item(String name, double price, int amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int newAmount){
        if(newAmount <= 13){
            this.amount = newAmount;
        } else {
            System.out.println("New amount is too big!");
        }
    }
}