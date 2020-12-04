////////////////////////////////////////////////////////////////////
// ALBERTO SINIGAGLIA 1193384
////////////////////////////////////////////////////////////////////
package it.unipd.tos.giveawayfilters;

import it.unipd.tos.Order;
import it.unipd.tos.User;
import it.unipd.tos.giveawayfilters.random.Random;

import java.util.HashSet;
import java.util.Set;

public class GiveAwayFilter10RandomOrdersFrom18to19ForMinors implements GiveAwayFilter{
  private final Set<User> giveAwayUsers;
  private final Random random;

  public GiveAwayFilter10RandomOrdersFrom18to19ForMinors(Random random) {
    this.giveAwayUsers = new HashSet<>();
    this.random = random;
  }

  @Override
  public boolean giveAway(Order order) {
    if( (!isValidUser(order.getUser())) ||
      giveAwayUsers.size() >= 10 ||
      order.getHour() != 18 ||
      random.no()){
      return false;
    }
    giveAwayUsers.add(order.getUser());
    return true;
  }

  private boolean isValidUser(User user){
    return !giveAwayUsers.contains(user) && user.getAge() < 18;
  }
}
