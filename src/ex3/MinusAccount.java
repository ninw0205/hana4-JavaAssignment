package ex3;

public class MinusAccount extends Account implements Transactable {

	public MinusAccount(int accountNo, String accountName, String depositor, int balance) {
		super(accountNo, accountName, depositor, balance);
	}

	@Override
	public void withdraw(int amount) {
		this.balance -= amount;
		System.out.printf("%s 통장에서 %,d원이 출금되었습니다.\n%s 통장의 잔액은 %,d원입니다.\n", getAccountName(), amount, getAccountName(),
			getBalance());
	}

	@Override
	public void transfer(Account account, int amount) {
		this.balance -= amount;
		account.deposit(amount);
		System.out.printf("%s 통장의 잔액은 %,d원입니다.\n", getAccountName(),
			getBalance());
	}
}
