package it.unipd.tos.giveawayfilters;

import it.unipd.tos.MenuItem;
import it.unipd.tos.Order;
import it.unipd.tos.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GiveAwayFilter10RandomOrdersFrom18to19ForMinorsTest {

  private List<MenuItem> items;
  private Order order; // valid order
  private GiveAwayFilter10RandomOrdersFrom18to19ForMinors ga;
  @Before
  public void setup(){
    items = List.of(
      new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d)
    );
    order = new Order(items, 18, 10, new User("Test1", "Test", 12));
    ga = new GiveAwayFilter10RandomOrdersFrom18to19ForMinors(() -> true);
  }
  @Test
  public void allowMinorAnd18To19(){
    assertTrue(ga.giveAway(order));
  }
  @Test
  public void denyNotMinor(){
    Order o1 = new Order(items, 18, 10, new User("Test1", "Test", 20));
    assertFalse(ga.giveAway(o1));
  }
  @Test
  public void denyBefore18(){
    Order o1 = new Order(items, 17, 10, new User("Test1", "Test", 12));
    assertFalse(ga.giveAway(o1));
  }
  @Test
  public void denyAfter19(){
    Order o1 = new Order(items, 19, 10, new User("Test1", "Test", 12));
    assertFalse(ga.giveAway(o1));
  }

  @Test
  public void allow10Users(){
    Order o1 = new Order(items, 18, 10, new User("Test1", "Test", 12));
    Order o2 = new Order(items, 18, 10, new User("Test2", "Test", 12));
    Order o3 = new Order(items, 18, 10, new User("Test3", "Test", 12));
    Order o4 = new Order(items, 18, 10, new User("Test4", "Test", 12));
    Order o5 = new Order(items, 18, 10, new User("Test5", "Test", 12));
    Order o6 = new Order(items, 18, 10, new User("Test6", "Test", 12));
    Order o7 = new Order(items, 18, 10, new User("Test7", "Test", 12));
    Order o8 = new Order(items, 18, 10, new User("Test8", "Test", 12));
    Order o9 = new Order(items, 18, 10, new User("Test9", "Test", 12));
    Order o10 = new Order(items, 18, 10, new User("Test10", "Test", 12));

    assertTrue("Non tutti gli ordini son stati regalati", ga.giveAway(o1) &&
      ga.giveAway(o2) &&
      ga.giveAway(o3) &&
      ga.giveAway(o4) &&
      ga.giveAway(o5) &&
      ga.giveAway(o6) &&
      ga.giveAway(o7) &&
      ga.giveAway(o8) &&
      ga.giveAway(o9) &&
      ga.giveAway(o10));
  }

  @Test
  public void denyMoreThan10ValidUsers(){
    Order o1 = new Order(items, 18, 10, new User("Test1", "Test", 12));
    Order o2 = new Order(items, 18, 10, new User("Test2", "Test", 12));
    Order o3 = new Order(items, 18, 10, new User("Test3", "Test", 12));
    Order o4 = new Order(items, 18, 10, new User("Test4", "Test", 12));
    Order o5 = new Order(items, 18, 10, new User("Test5", "Test", 12));
    Order o6 = new Order(items, 18, 10, new User("Test6", "Test", 12));
    Order o7 = new Order(items, 18, 10, new User("Test7", "Test", 12));
    Order o8 = new Order(items, 18, 10, new User("Test8", "Test", 12));
    Order o9 = new Order(items, 18, 10, new User("Test9", "Test", 12));
    Order o10 = new Order(items, 18, 10, new User("Test10", "Test", 12));
    Order o11 = new Order(items, 18, 10, new User("Test11", "Test", 12));
    ga.giveAway(o1);
    ga.giveAway(o2);
    ga.giveAway(o3);
    ga.giveAway(o4);
    ga.giveAway(o5);
    ga.giveAway(o6);
    ga.giveAway(o7);
    ga.giveAway(o8);
    ga.giveAway(o9);
    ga.giveAway(o10);
    ga.giveAway(o11);
    assertFalse("L'11esimo ordine doveva essere rifiutato", ga.giveAway(o11));
  }
  @Test
  public void denyTwoTimesTheSameUser(){
    var items2 = List.of(
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 1", 10d),
            new MenuItem(MenuItem.ItemType.GELATO, "gelato 2", 10d)
    );
    var order2 = new Order(items2, 18, 20, new User("Test1", "Test", 12));
    ga.giveAway(order);
    assertFalse(ga.giveAway(order2));
  }

  @Test
  public void denyIfProbabilitySaysNo(){
    ga = new GiveAwayFilter10RandomOrdersFrom18to19ForMinors(() -> false);
    assertFalse(ga.giveAway(order));
  }

}