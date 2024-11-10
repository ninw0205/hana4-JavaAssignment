package ex3;

public class BasicAccount extends Account implements Transactable {

	public BasicAccount(int accountNo, String accountName, String depositor, int balance) {
		super(accountNo, accountName, depositor, balance);
	}

	@Override
	public void withdraw(int amount) {
		if (this.balance < amount) {
			System.out.printf("잔액이 부족합니다! (잔액: %,d원)\n", getBalance());
			return;
		}
		this.balance -= amount;
		System.out.printf("%s 통장에서 %,d원이 출금되었습니다.\n%s 통장의 잔액은 %,d원입니다.\n", getAccountName(), amount, getAccountName(), getBalance());
	}

	@Override
	public void transfer(Account account, int amount) {
		account.deposit(amount);
		this.withdraw(amount);
	}
}
