package ex3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
	private final List<Account> accountList = new ArrayList<>();
	private final String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void checkAccounts(Account[] accounts) {
		accountList.addAll(Arrays.asList(accounts));
	}

	public List<Account> getAccountList() {
		return accountList;
	}
}
