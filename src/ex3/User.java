package ex3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
	private final List<Account> accountList = new ArrayList<>();

	public User(Account[] accounts) {
		accountList.addAll(Arrays.asList(accounts));
	}

	public List<Account> getAccountList() {
		return accountList;
	}
}
