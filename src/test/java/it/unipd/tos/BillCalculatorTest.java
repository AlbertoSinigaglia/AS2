package it.unipd.tos;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BillCalculatorTest {
  private static final double DELTA = 1e-4;
  BillCalculator calculator;
  @Before
  public void setup(){
    calculator = new BillCalculator();
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
}