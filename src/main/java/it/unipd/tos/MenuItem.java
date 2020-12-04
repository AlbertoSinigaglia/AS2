////////////////////////////////////////////////////////////////////
// ALBERTO SINIGAGLIA 1193384
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

public class MenuItem {
  public enum ItemType {
    GELATO, BUDINO, BEVANDA;
  }

  private final ItemType itemType;
  private final String name;
  private final double price;

  public MenuItem(ItemType itemType, String name, double price) throws IllegalArgumentException{
    if(price < 0){
      throw new IllegalArgumentException("Il prezzo non può essere < 0");
    } else if(itemType == null || name == null){
      throw new IllegalArgumentException("il tipo e il nome del prodotto non possono essere nulli");
    }
    this.itemType = itemType;
    this.name = name;
    this.price = price;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }
}
