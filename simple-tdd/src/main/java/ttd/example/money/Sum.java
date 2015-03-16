package ttd.example.money;

public class Sum implements Expression {

	public Money auged;
	
	public Money addend;
	
	public Sum(Money auged, Money addend) {
		this.auged = auged;
		this.addend = addend;
	}
	
	public Money reduce(Bank bank, String to) {
		int amount = auged.amount + addend.amount;
		return new Money(amount, to);
	}
}
