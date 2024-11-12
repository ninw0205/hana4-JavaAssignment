package ex3;

import java.util.List;

public class AccountService {
	private User user;

	public AccountService(String username) {
		this.user = new User(username);
	}

	public void setAccounts() {
		this.user.checkAccounts(
			new Account[] {
				new BasicAccount(1, "자유입출금", user.getName(), 0),
				new FixedDepositAccount(2, "정기예금", user.getName(), 50000000,
					new String[][] {{"1", "3.0"}, {"3", "3.35"}, {"6", "3.4"}, {"9", "3.35"}, {"12", "3.35"},
						{"24", "2.9"}, {"36", "2.9"}, {"48", "2.9"}}, 60),
				new MinusAccount(3, "마이너스", user.getName(), 0)
			});
	}

	public Account selectAccount(int accountNo) {
		for (Account account : user.getAccountList()) {
			if (account.getAccountNo() == accountNo) {
				return account;
			}
		}
		return null;
	}

	public List<Account> getAccountList() {
		return this.user.getAccountList();
	}
}
