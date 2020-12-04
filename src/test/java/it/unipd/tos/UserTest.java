package it.unipd.tos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user;
    int age;
    String name, surname;
    @Before
    public void setUp() {
        age = 21;
        name = "Alberto";
        surname = "Sinigaglia";
        user = new User(name, surname, age);
    }

    @Test
    public void getSurname() {
        assertEquals(age, user.getAge());
    }

    @Test
    public void getName() {
        assertEquals(name, user.getName());
    }

    @Test
    public void getAge() {
        assertEquals(surname, user.getSurname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForAgeLessThanZero() {
        new User("Test", "Test", -1);
    }

    @Test
    public void testEquals(){
        var u2 = new User(name, surname, age);
        assertTrue(u2.equals(user) && user.equals(u2));
    }
    @Test
    public void testEqualsSameObject(){
        assertTrue(user.equals(user));
    }
    @Test
    public void testNotEqualsWithNull(){
        assertFalse(user.equals(null));
    }
    @Test
    public void testNotEqualsIfDifferentName(){
        var u2 = new User(name+"something", surname, age);
        assertFalse(u2.equals(user));
    }
    @Test
    public void testNotEqualsIfDifferentSurname(){
        var u2 = new User(name, surname+"something", age);
        assertFalse(u2.equals(user));
    }
    @Test
    public void testNotEqualsIfDifferentAge(){
        var u2 = new User(name, surname, age+1);
        assertFalse(u2.equals(user));
    }

    @Test
    public void testNotEqualsIfDifferentClass(){
        assertFalse(user.equals(new Object()));
    }

    @Test
    public void testHashcode(){
        var u2 = new User(name, surname, age);
        assertEquals(u2.hashCode(), user.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void denyNullName() {
        new User(null, "Test", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void denyNullSurname() {
        new User("Test", null, 1);
    }


}