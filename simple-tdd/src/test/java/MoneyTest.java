import static org.junit.Assert.*;

import org.junit.Test;

import ttd.example.money.Dollar;


public class MoneyTest {

	// 1. iteration
	@Test
	public void testMultiplication() {
		Dollar five = new Dollar(5);
		Dollar product = five.times(2);
		assertEquals(10, product.amount);
		product = five.times(3);
		assertEquals(15, product.amount);
	}

}
