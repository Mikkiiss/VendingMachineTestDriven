package VendingMachineTestDrivenPackage ;

public class Cash implements Comparable<Cash> {

    private double value;
    private int amount;

    public Cash(double value, int amount) {
        this.value = value;
        this.amount = amount;
    }
    public int compareTo(Cash cash){
        if(this.getValue() > cash.getValue()){
            return 1;
        }
        else if (this.getValue() < cash.getValue()){
            return -1;
        }
        else{
            return 0;
        }
    }
    public double getValue(){
        return this.value;
    }
    public void SetValue(double value){
        this.value = value;
    }
    public int getAmount(){
        return this.amount;
    }
    public void setValue(double value){
        this.value = value;
    }
    public void setAmount(int newAmount){
        this.amount = newAmount;
    }

    public double getTotalValue(){
        return this.value * this.amount;
    }
}