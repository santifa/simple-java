package ttd.example.money;

public class Sum implements Expression {

	Expression auged;
	
	Expression addend;
	
	public Sum(Expression auged, Expression addend) {
		this.auged = auged;
		this.addend = addend;
	}
	
	public Money reduce(Bank bank, String to) {
		int amount = auged.reduce(bank, to).amount + addend.reduce(bank, to).amount;
		return new Money(amount, to);
	}
	
	public Expression plus(Expression addend) {
		return new Sum(this, addend);
	}
	
	public Expression times(int multiplier) {
		return new Sum(auged.times(multiplier), addend.times(multiplier));
	}
}
