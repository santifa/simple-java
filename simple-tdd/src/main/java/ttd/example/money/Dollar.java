package ttd.example.money;

public class Dollar {
	/*
	 * 1. Iteration
	 * plain methods without a line of logic.
	 */
	public int amount;
	
	public Dollar(int amount) {
		this.amount = amount;
	}
	
	public Dollar times(int multiplier) {
		return new Dollar(amount * multiplier);
	}
}
