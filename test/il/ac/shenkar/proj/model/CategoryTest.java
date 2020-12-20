package il.ac.shenkar.proj.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {
    private Category test;

    @Before
    public void setUp() throws Exception {
        test = new Category("Food");

    }

    @After
    public void tearDown() throws Exception {
        test = null;

    }

    @Test
    public void getCategory() {
        String expected = "Food";
        String real = test.getCategory();
        assertEquals(expected,real);
    }
}