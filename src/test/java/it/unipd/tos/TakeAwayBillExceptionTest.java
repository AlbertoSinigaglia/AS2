package it.unipd.tos;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TakeAwayBillExceptionTest {

    @Test
    public void testTipExceptionWithMessage() {
        TakeAwayBillException ex = new TakeAwayBillException("something wrong");
        assertEquals("TakeAwayBillException Occurred: something wrong", ex.toString());
    }

}