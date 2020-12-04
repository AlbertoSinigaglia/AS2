package it.unipd.tos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MenuItemTest {
  private static final double DELTA = 1e-3;
  MenuItem gelato;
  String nomeGelato = "Gelato";
  int costoGelato = 5;
  @Before
  public void setUp() {
    gelato = new MenuItem(MenuItem.ItemType.GELATO, nomeGelato, costoGelato);
  }

  @Test
  public void getItemType() {
    assertEquals(MenuItem.ItemType.GELATO, gelato.getItemType());
  }

  @Test
  public void getName() {
    assertEquals(nomeGelato, gelato.getName());
  }

  @Test
  public void getPrice() {
    assertEquals(costoGelato, gelato.getPrice(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativePrice() {
    new MenuItem(MenuItem.ItemType.BEVANDA, "bevanda", -5);
  }
  @Test(expected = IllegalArgumentException.class)
  public void denyNullNames() {
    new MenuItem(MenuItem.ItemType.BEVANDA, null, 5);
  }
  @Test(expected = IllegalArgumentException.class)
  public void denyNullItemType() {
    new MenuItem(null, "bevanda", 5);
  }

}