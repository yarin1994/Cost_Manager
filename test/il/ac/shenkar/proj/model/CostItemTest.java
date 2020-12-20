package il.ac.shenkar.proj.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class CostItemTest {

    private CostItem test;
    @Before
    public void setUp() throws Exception {
        test = new CostItem(Date.valueOf("2020-08-14"), "Food", "Hamburger", 100, Currency.EURO);
    }

    @After
    public void tearDown() throws Exception {
        test = null;
    }

    @Test
    public void testToString() {
        String expected = "CostItem{" +
                "id='0'" +
                ", date=2020-08-14" +
                ", category='Food'" +
                ", description='Hamburger'" +
                ", sum=100.0" +
                ", currency=EURO" +
                '}';
        String real = test.toString();
        assertEquals(expected,real);
    }

    @Test
    public void getDate() {
        String expected = "2020-08-14";
        String real = String.valueOf(test.getDate());
        assertEquals(expected,real);
    }

    @Test
    public void getCategory() {
        String expected = "Food";
        String real = test.getCategory();
        assertEquals(expected,real);
    }

    @Test
    public void getId() {
        String expected = "0";
        String real = String.valueOf(test.getId());
        assertEquals(expected,real);
    }

    @Test
    public void getDescription() {
        String expected = "Hamburger";
        String real = test.getDescription();
        assertEquals(expected,real);
    }

    @Test
    public void getSum() {
        String expected = "100.0";
        String real = String.valueOf(test.getSum());
        assertEquals(expected,real);
    }

    @Test
    public void getCurrency() {
        String expected = "EURO";
        String real = test.getCurrency();
        assertEquals(expected,real);
    }
}