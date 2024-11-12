package ex3;

import java.util.Scanner;

import ex3.exception.AccountException;
import ex3.exception.LowBalanceException;
import ex3.exception.TransferNotAllowedException;
import ex3.exception.WithdrawalNotAllowedException;

public class Bank {
	private Scanner scanner;
	private final User user = new User("홍길동");

	public void start() {
		if (this.scanner == null) {
			this.scanner = new Scanner(System.in);
		}
	}

	public void end() {
		if (this.scanner != null) {
			this.scanner.close();
		}
	}

	private void setAccounts() {
		this.user.checkAccounts(
			new Account[] {
				new BasicAccount(1, "자유입출금", user.getName(), 0),
				new FixedDepositAccount(2, "정기예금", user.getName(), 50000000,
					new String[][] {{"1", "3.0"}, {"3", "3.35"}, {"6", "3.4"}, {"9", "3.35"}, {"12", "3.35"},
						{"24", "2.9"}, {"36", "2.9"}, {"48", "2.9"}}, 60),
				new MinusAccount(3, "마이너스", user.getName(), 0)
			});
	}

	public void execute() {
		setAccounts();
		while (true) {
			System.out.print(">> 통장을 선택하세요(");
			user.getAccountList()
				.stream()
				.map(account -> account.getAccountNo() + ": " + account.getAccountName())
				.reduce((a, b) -> a + " " + b)
				.ifPresent(System.out::print);
			System.out.print(") ");
			String input = scanner.nextLine();
			if (input.equals("0") | input.equals("")) {
				System.out.println("금일 OneHanaBank는 업무를 종료합니다. 감사합니다.");
				break;
			}
			Account account = null;
			try {
				account = selectAccount(Integer.parseInt(input));
				if (!user.getAccountList()
					.stream()
					.map(Account::getAccountNo)
					.toList()
					.contains(Integer.parseInt(input))) {

					throw new AccountException();
				}

			} catch (Exception e) {
				System.out.println("계좌번호가 틀렸습니다.");
				continue;
			}
			if (account != null)
				printAccountInfo(account);
			try {
				accountMenu(account);
			} catch (AccountException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void printAccountInfo(Account account) {
		if (account instanceof MinusAccount) {
			System.out.printf("%s 통장 - 잔액: %,d원\n", account.getAccountName(), account.getBalance());
			return;
		}
		System.out.printf("%s 통장 (계좌번호: %s, %s: %,d원, 예금주:%s)%n", account.getAccountName(), account.getAccountNo(),
			(account instanceof FixedDepositAccount) ? "예치금" : "잔액", account.getBalance(), account.getDepositor());
	}

	private void accountMenu(Account account) throws AccountException {
		boolean isFixedDeposit = account instanceof FixedDepositAccount;
		while (true) {
			System.out.printf("> %s (+: %s, -: 출금, T: 이체, I: 정보) ",
				isFixedDeposit ? "정기 예금이 만기되었습니다." : "원하시는 업무는?",
				isFixedDeposit ? "만기처리" : "입금");
			String tmp = scanner.nextLine();
			if (tmp.equals("") || tmp.equals("0")) {
				return;
			}
			try {
				switch (tmp) {
					case "+" -> {
						if (isFixedDeposit) {
							if (handleFixedDeposit((FixedDepositAccount) account)) {
								return;
							}
						} else {
							handleDeposit(account);
						}
					}
					case "-" -> handleWithdrawal(account);
					case "T" -> handleTransfer(account);
					case "I" -> {
						printAccountInfo(account);
						if (isFixedDeposit) {
							((FixedDepositAccount)account).printRates();
						}
					}
					default -> System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
				}
			} catch (AccountException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void handleDeposit(Account account) throws AccountException {
		if (account instanceof FixedDepositAccount) {
			handleFixedDeposit((FixedDepositAccount) account);
		} else {
			while (true) {
				System.out.print("입금 하실 금액은? ");
				String input = scanner.nextLine();
				if (input.equals("") || input.equals("0")) {
					return;
				}
				try {
					int amount = Integer.parseInt(input);
					account.deposit(amount);
					break;
				} catch (NumberFormatException e) {
					System.out.println("올바른 숫자를 입력해주세요.");
				}
			}
		}
	}

	private boolean handleFixedDeposit(FixedDepositAccount account) {
		while (true) {
			System.out.printf("예치 개월 수를 입력하세요? (1 ~ %d개월) ", account.getMaxYear());
			String y = scanner.nextLine();
			if (y.equals("") || y.equals("0")) {
				return false;
			}
			try {
				int year = Integer.parseInt(y);
				while (true) {
					System.out.printf("%d개월(적용금리 %s%%)로 만기 처리하시겠어요? (y/n) ", year, account.getRateOfMaxYear(year));
					String b = scanner.nextLine();
					if (b.equalsIgnoreCase("Y")) {
						Account to = sendMoneyWhere(account);
						if (to == null) {
							return false;
						}
						account.finishAccount(account.getRateOfMaxYear(year), to);
						System.out.println(account.getAccountName() + " 통장은 해지되었습니다. 감사합니다.");
						user.getAccountList().remove(account);
						return true;
					} else if (b.equalsIgnoreCase("N")) {
						break;
					} else if (b.equals("") || b.equals("0")) {
						return false;
					} else {
						System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("올바른 숫자를 입력해주세요.");
			}
		}
	}

	private void handleWithdrawal(Account account) throws AccountException {
		if (!(account instanceof Transactable)) {
			throw new WithdrawalNotAllowedException();
		}

		while (true) {
			System.out.print("출금 하실 금액은? ");
			String input = scanner.nextLine();
			if (input.equals("") || input.equals("0")) {
				return;
			}
			try {
				int amount = Integer.parseInt(input);
				((Transactable)account).withdraw(amount);
				break;
			} catch (LowBalanceException e) {
				System.out.print(e.getMessage());
				System.out.printf(" (잔액: %,d원)\n", account.getBalance());
			} catch (NumberFormatException e) {
				System.out.println("올바른 숫자를 입력해주세요.");
			}
		}
	}

	private void handleTransfer(Account account) throws AccountException {
		if (!(account instanceof Transactable)) {
			throw new TransferNotAllowedException();
		}

		Account to = sendMoneyWhere(account);
		if (to == null) {
			return;
		}

		while (true) {
			System.out.printf("%s 통장에 보낼 금액은? ", to.getAccountName());
			String input = scanner.nextLine();
			if (input.equals("") || input.equals("0")) {
				return;
			}
			try {
				int amount = Integer.parseInt(input);
				((Transactable)account).transfer(to, amount);
				break;
			} catch (LowBalanceException e) {
				System.out.print(e.getMessage());
				System.out.printf(" (잔액: %,d원)\n", account.getBalance());
			} catch (NumberFormatException e) {
				System.out.println("올바른 숫자를 입력해주세요.");
			}
		}
	}

	private Account sendMoneyWhere(Account account) {
		System.out.print("어디로 보낼까요? (");
		user.getAccountList()
			.stream()
			.filter(acc -> acc.getAccountNo() != account.getAccountNo())
			.map(acc -> acc.getAccountNo() + ": " + acc.getAccountName())
			.reduce((a, b) -> a + " " + b)
			.ifPresent(System.out::print);
		System.out.print(") ");
		String input = scanner.nextLine();
		if (input.equals("") || input.equals("0")) {
			return null;
		}
		try {
			int num = Integer.parseInt(input);
			return user.getAccountList()
				.stream()
				.filter(acc -> acc.getAccountNo() == num)
				.findFirst()
				.orElse(null);
		} catch (NumberFormatException e) {
			System.out.println("올바른 숫자를 입력해주세요.");
			return null;
		}
	}

	private Account selectAccount(int accountNo) {
		for (Account account : user.getAccountList()) {
			if (account.getAccountNo() == accountNo) {
				return account;
			}
		}
		return null;
	}
}
