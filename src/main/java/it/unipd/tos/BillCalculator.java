////////////////////////////////////////////////////////////////////
// ALBERTO SINIGAGLIA 1193384
////////////////////////////////////////////////////////////////////
package it.unipd.tos;

import it.unipd.tos.giveawayfilters.GiveAwayFilter;

import java.util.Comparator;
import java.util.stream.Stream;

public class BillCalculator implements TakeAwayBill {
  private Order order;
  private final GiveAwayFilter filter;

  public BillCalculator(GiveAwayFilter filter) {
    this.filter = filter;
  }
  @Override
  public double getOrderPrice(Order order) throws TakeAwayBillException, IllegalArgumentException {
    if(order == null){
      throw new IllegalArgumentException("L'ordine non può essere null");
    }
    this.order = order;
    if (this.areThereMoreThan30Elements()) {
      throw new TakeAwayBillException("Non puoi ordinare più di 30 elementi");
    }
    if(this.filter.giveAway(order)){
      return 0;
    }
    double total = this.getTotal();
    if (this.isTheOrderLessThan10Euros()) {
      total += 0.5;
    }
    return total;
  }

  private double getTotal() {
    double tot = this.getSubTotal();
    if (this.areThereMoreThan5IceCreams()) {
      tot -= this.getPriceCheapestIceCream() / 2;
    }
    if (this.areThereMoreThan50EurosInIceCreamsAndPuddings()) {
      tot *= 0.9;
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

  private boolean areThereMoreThan50EurosInIceCreamsAndPuddings() {
    return this.order.getItemsOrdered().stream().filter(
            el -> el.getItemType().equals(MenuItem.ItemType.GELATO) ||
                    el.getItemType().equals(MenuItem.ItemType.BUDINO)
    ).mapToDouble(MenuItem::getPrice).sum() > 50;
  }

  private boolean areThereMoreThan30Elements() {
    return this.order.getItemsOrdered().size() > 30;
  }

  private boolean isTheOrderLessThan10Euros() {
    return this.getTotal() < 10;
  }
}
