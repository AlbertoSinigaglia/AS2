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
    return this.getSubTotal();
  }

  private double getSubTotal() {
    Stream<Double> integerStream = order.getItemsOrdered().stream().map(MenuItem::getPrice);
    return integerStream.reduce(0d, Double::sum);
  }
}
