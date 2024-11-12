package ex3;

import ex3.exception.AccountException;
import ex3.exception.LowBalanceException;

public class BasicAccount extends Account implements Transactable {

	public BasicAccount(int accountNo, String accountName, String depositor, int balance) {
		super(accountNo, accountName, depositor, balance);
	}

	@Override
	public void withdraw(int amount) throws AccountException {
		if (this.balance < amount) {
			throw new LowBalanceException();
		}
		this.balance -= amount;
		System.out.printf("%s 통장에서 %,d원이 출금되었습니다.\n%s 통장의 잔액은 %,d원입니다.\n", getAccountName(), amount, getAccountName(),
			getBalance());
	}

	@Override
	public void transfer(Account account, int amount) throws LowBalanceException {
		if (this.balance < amount) {
			throw new LowBalanceException();
		}

		this.balance -= amount;
		account.deposit(amount);
		System.out.printf("%s 통장의 잔액은 %,d원입니다.\n", getAccountName(),
			getBalance());
	}
}
