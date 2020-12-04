package it.unipd.tos;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OrderTest {
    Order order;
    int h;
    int m;
    User user;
    List<MenuItem> orderedElements;
    @Before
    public void setUp(){
        h = 18;
        m = 5;
        user = new User("Test name", "Test Surname", 20);
        MenuItem mi1 = new MenuItem(MenuItem.ItemType.GELATO, "Cioccolato", 12.5);
        MenuItem mi2 = new MenuItem(MenuItem.ItemType.GELATO, "Cioccolato", 12.5);
        MenuItem mi3 = new MenuItem(MenuItem.ItemType.GELATO, "Cioccolato", 12.5);
        orderedElements = List.of(mi1, mi2, mi3);
        order = new Order(orderedElements, h, m, user);
    }

    @Test
    public void getHour(){
        assertEquals(h, order.getHour());
    }

    @Test
    public void getMinute(){
        assertEquals(m, order.getMinute());
    }

    @Test
    public void getUser(){
        assertEquals(user, order.getUser());
    }

    @Test
    public void getItemsOrdered(){
        assertArrayEquals(orderedElements.toArray(), order.getItemsOrdered().toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeHour(){
        new Order(orderedElements, -1, m, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHourGreaterThan23(){
        new Order(orderedElements, 24, m, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMinute(){
        new Order(orderedElements, h, -1, user);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMinuteGreaterThan59(){
        new Order(orderedElements, h, 60, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMinuteAndNegativeHour(){
        new Order(orderedElements, -1, -1, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHourGreaterThan23AndMinuteGreaterThan59(){
        new Order(orderedElements, 24, 60, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyList(){
        new Order(List.of(), h, m, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMenuItemInOrder(){
        List<MenuItem> mi = new ArrayList<>();
        mi.add(new MenuItem(MenuItem.ItemType.GELATO, "Cioccolato", 12.5));
        mi.add(null);
        new Order(mi, h, m, user);
    }
    @Test(expected = IllegalArgumentException.class)
    public void denyNullList(){
        new Order(null, h, m, user);
    }
    @Test(expected = IllegalArgumentException.class)
    public void denyNullUser(){
        new Order(
                List.of(new MenuItem(MenuItem.ItemType.GELATO, "Cioccolato", 12.5)),
                h,
                m,
                null
        );
    }

}