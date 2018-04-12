package VendingMachineTestDrivenPackage;

import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    private List<Item> items;
    private List<Cash> balance;
    private List<Cash> totalAmount; //amount in before selectItem

    public VendingMachine() throws Exception{
        this.items = new ArrayList<Item>();
        this.balance = new ArrayList<Cash>();
        this.totalAmount = new ArrayList<Cash>();

        //Create items
        addItem("Balpen with NHL Stenden Logo", 0.25, 0, 0);
        addItem("Notebook with NHL Stenden Logo", 2.45, 0, 1);
        addItem("Whiteboard Markers", 3.65, 0, 2);
        addItem("Pencil", 0.10, 0, 3);
        addItem("Key cord with NHL Stenden Logo", 1.85, 0, 4);
        addItem("Fine liner", 0.50, 0, 5);
        addItem("USB-Stick 16GB", 6.95, 0, 6);
        addItem("Bambook reusable Notebook", 18.45, 0, 7);

        //Create cash
        List<Cash> list = new ArrayList<Cash>();
        Cash fiveCent = new Cash(0.05, 0);
        Cash tenCent = new Cash(0.10, 0);
        Cash twentyCent = new Cash(0.20, 0);
        Cash fiftyCent = new Cash(0.50, 0);
        Cash oneEuro = new Cash(1, 0);
        Cash twoEuro = new Cash(2, 0);
        Cash fiveEuro = new Cash(5, 0);
        Cash tenEuro = new Cash(10, 0);
        //Add to list
        list.add(tenEuro);
        list.add(fiveEuro);
        list.add(twoEuro);
        list.add(oneEuro);
        list.add(fiftyCent);
        list.add(twentyCent);
        list.add(tenCent);
        list.add(fiveCent);
        //Sets new balance with 0 cash
        setBalanceList(list);
    }

    public boolean checkItemIn(int index){
        Item item = items.get(index);
        if(item.getAmount() != 0){
            return  true;
        } else {
            return false;
        }
    }

    public List<Cash> getBalanceList(){
        return this.balance;
    }

    public List<Item> getItemList(){
        return this.items;
    }

    public void setBalanceList(List<Cash> newBalance) {
        this.balance = newBalance;
    }

    public void addItem(String name, int amount) throws Exception{
        //check if item is already in, if its not then create it if it is only increase amount
        for(Item item:items){
            if(name == item.getName()){
                int newAmount = item.getAmount() + amount;
                if(newAmount <= 13){
                    item.setAmount(newAmount);
                    return;
                } else {
                    item.setAmount(13);
                    throw new Exception("Item already filled to the maximum amount");
                }
            }
        }
    }

    public void addItem(String name, double price, int amount, int index) throws Exception{
        //There can only be 8 items
        if(index < 8){
            Item item = new Item(name, price, amount);
            items.add(index, item);
        } else {
            throw new Exception("Incorrect index!");
        }
    }

    public void removeItem(int index){
        Item item = items.get(index);
        int newAmount = item.getAmount() - 1;
        item.setAmount(newAmount);
    }

    public double getItemPrice(int index) {
        Item item = items.get(index);
        return item.getPrice();
    }

    public double getTotalAmount(List<Cash> list){
        double totalCash = 0.0;
        for(Cash cash: list){
            totalCash += cash.getTotalValue();
        }
        return totalCash;
    }

    public void insertCoin(double value, int amount) throws Exception{
        // check if it exists
        if(value != 10.0 && value != 5.0 && value != 2.0 && value != 1.0 && value != 0.50 &&
                value != 0.20 && value != 0.10 && value != 0.05)
        {
            throw new Exception("Value is not valid!");
        }
        this.totalAmount.add(new Cash(value, amount));
    }

    public List<Cash> sortHighestToLowest(List<Cash> cash){
        Cash highestCoin;
        List<Cash> sortedCash = new ArrayList<Cash>();
        for(int i=0;i<cash.size();i++){
            highestCoin = new Cash(0,0);
            for(Cash coin : cash){
                if(coin.getValue() > highestCoin.getValue()){
                    highestCoin = coin;
                    coin.setValue(0);
                }
            }
            sortedCash.add(highestCoin);
        }
        return cash;
    }
    //Order of execution:
    //-check item is in
    //-check money is enough
    //-check change --> not enough change then prompt user
    //-depending on previous step we will add the money and give the change back
    public void selectItem(int index, String answer){
        if(checkItemIn(index)){
            //Set amountinserted and item price
            double totalCash = getTotalAmount(totalAmount);
            double itemPrice = getItemPrice(index);
            //If enough money is inserted, insert all the cash in the vending machine
            if(totalCash >= itemPrice){
                //If no change is required, simply add the cash to the balance
                if(totalCash == itemPrice){
                    for(Cash insertedCoin: totalAmount){
                        for(Cash balanceCoin : balance){
                            if(insertedCoin.getValue() == balanceCoin.getValue()){
                                balanceCoin.setAmount(balanceCoin.getAmount() + insertedCoin.getAmount());
                            }
                        }
                    }
                    removeItem(index);
                }
                //Otherwise calculate the change and return it
                else{
                    double difference = totalCash - itemPrice;
                    Collections.sort(totalAmount, Collections.reverseOrder());
                    //Insert all coins in the vendingmachine
                    for(Cash insertedCoin : totalAmount){
                        for(Cash balanceCoin : balance){
                            if(insertedCoin.getValue() == balanceCoin.getValue()){
                                balanceCoin.setAmount(balanceCoin.getAmount() + insertedCoin.getAmount());
                            }
                        }
                    }
                    //List<Cash> sortedBalance = sortHighestToLowest(balance);
                    Collections.sort(balance, Collections.reverseOrder());
                    DecimalFormat df = new DecimalFormat("##.##");
                    df.setRoundingMode(RoundingMode.HALF_DOWN);
                    difference = Double.parseDouble(df.format(difference));
                    //Return the change going
                    for(Cash balanceCoin : balance){
                            while(balanceCoin.getAmount() > 0 && balanceCoin.getValue() <= difference){
                                balanceCoin.setAmount(balanceCoin.getAmount() - 1);
                                difference -= balanceCoin.getValue();
                                difference = Double.parseDouble(df.format(difference));
                            }
                    }
                    removeItem(index);

                }
            }
        }
    }
}