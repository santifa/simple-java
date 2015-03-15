package ttd.example.money;

public class Bank {

	public Money reduce(Expression source, String to) {
		return source.reduce(to);
	}
}
