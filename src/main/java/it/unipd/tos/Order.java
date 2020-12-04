////////////////////////////////////////////////////////////////////
// ALBERTO SINIGAGLIA 1193384
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.List;
import java.util.Objects;

public class Order {
  private final List<MenuItem> itemsOrdered;
  private final int h;
  private final int m;
  private final User user;

  public Order(List<MenuItem> itemsOrdered, int h, int m, User user)  throws IllegalArgumentException {
    if(itemsOrdered == null){
      throw new IllegalArgumentException("itemsOrdered non può essere null");
    } else if(itemsOrdered.size() == 0){
      throw new IllegalArgumentException("Devi ordinare almeno un prodotto");
    } else if (isInvalidHour(h) || IsInvalidMinute(m)){
      throw new IllegalArgumentException("Ora non valida");
    } else if(itemsOrdered.stream().anyMatch(Objects::isNull)){
      throw new IllegalArgumentException("L'ordine non può contenere MenuItems nulli");
    } else if(user == null){
      throw new IllegalArgumentException("l'user non può essere null");
    }
    this.itemsOrdered = itemsOrdered;
    this.h = h;
    this.m = m;
    this.user = user;
  }

  private boolean IsInvalidMinute(int m) {
    return m < 0 || m > 59;
  }

  private boolean isInvalidHour(int h) {
    return h < 0 || h > 23;
  }

  public List<MenuItem> getItemsOrdered() {
    return itemsOrdered;
  }

  public int getHour() {
    return h;
  }

  public int getMinute() {
    return m;
  }

  public User getUser() {
    return user;
  }
}
