package VendingMachineTestDrivenPackage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    @org.junit.Before
    public void setUp() throws Exception {

        this.vendingMachine = new VendingMachine();

        this.vendingMachine.addItem("Balpen with NHL Stenden Logo", 1);
        this.vendingMachine.addItem("Notebook with NHL Stenden Logo", 4);
        this.vendingMachine.addItem("Whiteboard Markers", 1);
        this.vendingMachine.addItem("Pencil", 6);
        this.vendingMachine.addItem("Key cord with NHL Stenden Logo", 3);
        this.vendingMachine.addItem("Fine liner", 4);
        this.vendingMachine.addItem("USB-Stick 16GB", 2);
        this.vendingMachine.addItem("Bambook reusable Notebook", 6);

        //Create cash
        List<Cash> list = new ArrayList<Cash>();

        Cash fiveCent = new Cash(0.05, 20);
        Cash tenCent = new Cash(0.10, 20);
        Cash twentyCent = new Cash(0.20, 20);
        Cash fiftyCent = new Cash(0.50, 20);
        Cash oneEuro = new Cash(1.0, 20);
        Cash twoEuro = new Cash(2.0, 20);
        Cash fiveEuro = new Cash(5.0, 20);
        Cash tenEuro = new Cash(10.0, 20);

        //Add to list
        list.add(fiveCent);
        list.add(tenCent);
        list.add(twentyCent);
        list.add(fiftyCent);
        list.add(oneEuro);
        list.add(twoEuro);
        list.add(fiveEuro);
        list.add(tenEuro);

        //Sets new balance with 0 cash
        this.vendingMachine.setBalanceList(list);
    }

    @Test
    public void rightAmountAfterChange() throws Exception {
    	//Set pattern for rounding doubles
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        for(int i = 0; i < vendingMachine.getItemList().size();i++){
            //Arrange
            double balanceBefore = this.vendingMachine.getTotalAmount(this.vendingMachine.getBalanceList());
            this.vendingMachine.insertCoin(5, 3);
            //Act
            this.vendingMachine.selectItem(i,"y");
            //Assert
            //Round the difference in balance
            assertThat(Double.parseDouble(df.format(this.vendingMachine.getTotalAmount(this.vendingMachine.getBalanceList())- balanceBefore )), is(vendingMachine.getItemList().get(i).getPrice()));
        }

    }

    @Test
    public void itemReturned() throws Exception {
        //Arrange
    	this.vendingMachine.insertCoin(0.5, 1);
    	
        //Act
    	this.vendingMachine.selectItem(0,"y");
    	
        //Assert
    	assertThat(this.vendingMachine.checkItemIn(0), is(false));
    }

    @Test
    public void fillMachine() throws Exception {
        //Arrange
    	
        //Act
        this.vendingMachine.addItem("Pencil", 2);
        this.vendingMachine.addItem("Key cord with NHL Stenden Logo", 3);
        this.vendingMachine.addItem("Fine liner", 1);
        
        List<Item> items = this.vendingMachine.getItemList();
        
        //Assert
        assertThat(items.get(4).getAmount(), is(6));
        assertThat(items.get(5).getAmount(), is(5));
        assertThat(items.get(3).getAmount(), is(8));
    }

    @Test(expected = Exception.class)
    public void nonExistingCoin() throws Exception {
        //Arrange
    	
        //Act
    	this.vendingMachine.insertCoin(0.30, 1);
    	
        //Assert
    }
    
    @Test
    public void checkItemsExist() {
        //Arrange
    	
        //Act
        
        //Assert
    	assertThat(this.vendingMachine.checkItemIn(0), is(true));
    	assertThat(this.vendingMachine.checkItemIn(1), is(true));
    	assertThat(this.vendingMachine.checkItemIn(2), is(true));
    	assertThat(this.vendingMachine.checkItemIn(3), is(true));
    	assertThat(this.vendingMachine.checkItemIn(4), is(true));
    	assertThat(this.vendingMachine.checkItemIn(5), is(true));
    	assertThat(this.vendingMachine.checkItemIn(6), is(true));
    	assertThat(this.vendingMachine.checkItemIn(7), is(true));
    }
    
    @Test(expected = Exception.class)
    public void wrongIndexAddItem() throws Exception {
        //Arrange
    	
        //Act
    	this.vendingMachine.addItem("Bambook reusable Notebook", 18.45, 6, 8);
    	
        //Assert
    }
    
    @Test(expected = Exception.class)
    public void wrongIndexAddItems() throws Exception {
        //Arrange
    	
        //Act
    	this.vendingMachine.addItem("Bambook reusable Notebook", 20);
    	
        //Assert
    }
    
    @Test
    public void checkTotalAmount() {

        //Arrange

        List<Cash> list = new ArrayList<Cash>();
        Cash fiveCent = new Cash(0.05, 1);
        Cash tenCent = new Cash(0.10, 2);
        Cash twentyCent = new Cash(0.20, 0);
        Cash fiftyCent = new Cash(0.50, 2);
        Cash oneEuro = new Cash(1, 0);
        Cash twoEuro = new Cash(2, 1);
        Cash fiveEuro = new Cash(5, 3);
        Cash tenEuro = new Cash(10, 1);

        //Add to list
        list.add(tenEuro);
        list.add(fiveEuro);
        list.add(twoEuro);
        list.add(oneEuro);
        list.add(fiftyCent);
        list.add(twentyCent);
        list.add(tenCent);
        list.add(fiveCent);

        
        //Act
        
        //Assert
    	assertThat(this.vendingMachine.getTotalAmount(list), is(28.25));
    }
}