package ex3;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedDepositAccount extends Account {
	private final Map<String, String> interestRate = new LinkedHashMap<>();
	private final int maxYear;
	public FixedDepositAccount(int accountNo, String accountName, String depositor, int balance, String[][] rates, int maxYear) {
		super(accountNo, accountName, depositor, balance);
		for (String[] rate : rates) {
			interestRate.put(rate[0], rate[1]);
		}
		this.maxYear = maxYear;
	}

	public int getMaxYear() {
		return maxYear;
	}

	public String getRateOfMaxYear(int year) {
		String k = null;
		for (String key : interestRate.keySet()) {
			if (year >= Integer.parseInt(key)) {
				k = key;
			}
		}
		return interestRate.get(k);
	}

	public void finishAccount(String rate, Account account) {
		int money = (int)(Double.parseDouble(rate) * getBalance() / 100) + getBalance();
		account.deposit(money);
		this.balance = 0;
		System.out.println(getAccountName() + " 통장은 해지되었습니다. 감사합니다.");
	}

	public void printRates() {
		StringBuilder sb = new StringBuilder("* 예치 개월에 따른 적용 금리\n");
		int idx = 0;
		for (String key : interestRate.keySet()) {
			sb.append("		%s개월 이상	%s%%".formatted(key, interestRate.get(key)));
			idx++;
			if (idx != interestRate.size()) {
				sb.append('\n');
			}
		}
		System.out.println(sb);
	}
}
