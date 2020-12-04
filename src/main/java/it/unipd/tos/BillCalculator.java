////////////////////////////////////////////////////////////////////
// ALBERTO SINIGAGLIA 1193384
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import java.util.Comparator;
import java.util.stream.Stream;

public class BillCalculator implements TakeAwayBill {
  private Order order;

  @Override
  public double getOrderPrice(Order order) throws TakeAwayBillException, IllegalArgumentException {
    if(order == null){
      throw new IllegalArgumentException("L'ordine non può essere null");
    }
    this.order = order;
    return this.getTotal();
  }

  private double getTotal() {
    double tot = this.getSubTotal();
    if (this.areThereMoreThan5IceCreams()) {
      tot -= this.getPriceCheapestIceCream() / 2;
    }
    return tot;
  }

  private double getSubTotal() {
    Stream<Double> integerStream = order.getItemsOrdered().stream().map(MenuItem::getPrice);
    return integerStream.reduce(0d, Double::sum);
  }

  private boolean areThereMoreThan5IceCreams() {
    return this.order.getItemsOrdered().stream().filter(
            el -> el.getItemType().equals(MenuItem.ItemType.GELATO)
    ).count() > 5;
  }

  private double getPriceCheapestIceCream() {
    return this.order.getItemsOrdered().stream().filter(
            el -> el.getItemType().equals(MenuItem.ItemType.GELATO)
    ).min(
            Comparator.comparing(MenuItem::getPrice)
    ).get().getPrice();
  }
}
