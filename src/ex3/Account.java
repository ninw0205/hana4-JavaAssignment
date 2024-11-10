package ex3;

public class Account {
	protected int accountNo;
	protected String accountName;
	protected String depositor;
	protected int balance;

	public Account(int accountNo, String accountName, String depositor, int balance) {
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.depositor = depositor;
		this.balance = balance;
	}

	public void deposit(int amount) {
		this.balance += amount;
		System.out.printf("%s 통장에 %,d원이 입금되었습니다.\n", getAccountName(), amount);
	}

	public int getAccountNo() {
		return accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getDepositor() {
		return depositor;
	}

	public int getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "%s 통장 (계좌번호: %s, 예치금: %,d원, 예금주:%s)".formatted(getAccountName(), getAccountNo(), getBalance(),
			getDepositor());
	}
}
