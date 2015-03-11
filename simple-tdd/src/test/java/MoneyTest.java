import static org.junit.Assert.*;

import org.junit.Test;

import ttd.example.money.Dollar;


public class MoneyTest {

	// 1. iteration
	@Test
	public void testMultiplication() {
		Dollar five = new Dollar(5);
		five.times(2);
		assertEquals(10, five.amount);
	}

}
