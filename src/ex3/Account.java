package ex3;

public class Account implements Comparable<Account> {
	private final int accountNo;
	private final String accountName;
	private final String depositor;
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
	public int compareTo(Account account) {
		return this.getAccountNo() - account.getAccountNo();
	}
}
