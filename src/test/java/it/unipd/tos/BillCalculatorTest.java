package it.unipd.tos;

import it.unipd.tos.giveawayfilters.GiveAwayFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BillCalculatorTest {
  private static final double DELTA = 1e-4;
  BillCalculator calculator;
  @Before
  public void setup(){
    calculator = new BillCalculator(order -> false); // non regalare mai
  }

  /**
   * Definizione di "basic order":
   *    con meno di 5 gelati
   *    il cui totale è < 50€ ma > 10€
   *    con meno di 30 elementi
   *
   */
  @Test
  public void testForBasicOrder() throws TakeAwayBillException, IllegalAccessException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d),
            new MenuItem(MenuItem.ItemType.BUDINO, "budino 1", 5.5d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 7d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 3d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 3", 7.7d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(33.2d, calculator.getOrderPrice(order), DELTA);
  }
  @Test(expected = IllegalArgumentException.class)
  public void denyNullOrder() throws IllegalArgumentException, TakeAwayBillException {
    calculator.getOrderPrice(null);
  }

  @Test
  public void testForBasicOrderWithMoreThan5IceCreams() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 7d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 3", 7.7d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 4", 4d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 5", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 6", 1d),
            new MenuItem(MenuItem.ItemType.BUDINO, "budino 1", 5.5d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 3d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(
            40.2 - 0.5, // totale - 50% gelato meno costoso
            calculator.getOrderPrice(order),
            DELTA);
  }
  @Test
  public void testForBasicOrderWithTotalMoreThan50WithoutDrinks() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 70d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 3", 7.7d),
            new MenuItem(MenuItem.ItemType.BUDINO, "budino 1", 5.5d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 3d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(
            96.2 - 9.62, // totale - 10%
            calculator.getOrderPrice(order),
            DELTA);
  }

  @Test
  public void testForOrderWithTotalMoreThan50WithoutDrinksAndWithMoreThan5IceCreams() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 70d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 3", 7.7d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 4", 4d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 5", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 6", 1d),
            new MenuItem(MenuItem.ItemType.BUDINO, "budino 1", 5.5d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 3d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(
            (103.2 - 0.5) * 0.9, // (totale - costo gelato meno costoso) * 0.9 che sarebbe -10%
            calculator.getOrderPrice(order),
            DELTA);
  }

  @Test
  public void deny10PercentDiscountIfThereAreLessThan50WithoutDrinks() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 300d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(
            310, // sconto 10% non applicato
            calculator.getOrderPrice(order),
            DELTA);
  }

  @Test(expected = TakeAwayBillException.class)
  public void testNoMoreThan30ElementsAllowed() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 1d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 0.50d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 3", 0.7d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 4", 1d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 5", 0.5d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 6", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 7", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 8", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 9", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 10", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 11", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 12", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 13", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 14", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 15", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 16", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 17", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 18", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 19", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 20", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 21", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 22", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 23", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 24", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 25", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 26", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 27", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 28", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 29", 2d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 30", 2d),
            new MenuItem(MenuItem.ItemType.BUDINO, "budino 1", 1.2d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 1d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    calculator.getOrderPrice(order);
  }

  @Test
  public void testForBasicOrderWithTotalLessThan10() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 1d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 7d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(
            8 + 0.5, // totale + commissione
            calculator.getOrderPrice(order),
            DELTA);
  }

  @Test
  public void testForOrderWithTotalLessThan10AndWithMoreThan5IceCreams() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 1d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 0.50d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 3", 0.7d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 4", 1d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 5", 0.5d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 6", 2d),
            new MenuItem(MenuItem.ItemType.BUDINO, "budino 1", 1.2d),
            new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda 1", 1d)
    );
    Order order = new Order(items, 19, 10, new User("Test", "Test", 20));
    assertEquals(
            7.9 - 0.25 + 0.5, // totale - 50% gelato meno costoso + commissione 0.5
            calculator.getOrderPrice(order),
            DELTA);
  }

  @Test
  public void giveAwayIfFilterIsTrue() throws TakeAwayBillException {
    var items = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d)
    );
    var order = new Order(items, 18, 10, new User("Test1", "Test", 12));
    assertEquals(new BillCalculator(new GiveAwayFilter() {
      @Override
      public boolean giveAway(Order order) {
        return true;
      }
    }).getOrderPrice(order), 0d, DELTA);
  }
}