package ex3;

import ex3.exception.AccountException;
import ex3.exception.LowBalanceException;
import ex3.exception.TransferNotAllowedException;
import ex3.exception.WithdrawalNotAllowedException;

public class Bank {
	private final InputHandler inputHandler;
	private final AccountService accountService;

	public Bank() {
		this.inputHandler = new InputHandler();
		this.accountService = new AccountService("홍길동");
	}

	public void execute() {
		inputHandler.startScan();
		accountService.setAccounts();
		while (true) {
			Account account = null;
			try {
				int input = inputHandler.getIntInputWithQuestion(">> 통장을 선택하세요(" + accountService.getAccountList()
					.stream()
					.map(acc -> acc.getAccountNo() + ": " + acc.getAccountName())
					.reduce((a, b) -> a + " " + b)
					.orElse("") + ") ");
				if (input == 0) {
					System.out.println("금일 OneHanaBank는 업무를 종료합니다. 감사합니다.");
					break;
				}
				account = accountService.selectAccount(input);
				if (!accountService.getAccountList()
					.stream()
					.map(Account::getAccountNo)
					.toList()
					.contains(input)) {

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
		inputHandler.endScan();
	}

	private void printAccountInfo(Account account) {
		if (account instanceof MinusAccount) {
			System.out.printf("%s 통장 - 잔액: %,d원\n", account.getAccountName(), account.getBalance());
		} else {
			System.out.printf("%s 통장 (계좌번호: %s, %s: %,d원, 예금주:%s)%n", account.getAccountName(), account.getAccountNo(),
				(account instanceof FixedDepositAccount) ? "예치금" : "잔액", account.getBalance(), account.getDepositor());
		}
	}

	private void accountMenu(Account account) throws AccountException {
		boolean isFixedDeposit = account instanceof FixedDepositAccount;
		while (true) {
			String input = inputHandler.getInputWithQuestion("> %s (+: %s, -: 출금, T: 이체, I: 정보) ".formatted(
				isFixedDeposit ? "정기 예금이 만기되었습니다." : "원하시는 업무는?",
				isFixedDeposit ? "만기처리" : "입금"));
			if (input == null) {
				return;
			}
			try {
				switch (input) {
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
					case "T", "t" -> handleTransfer(account);
					case "I", "i" -> {
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
				try {
					int input = inputHandler.getIntInputWithQuestion("입금 하실 금액은? ");
					if (input == 0) {
						return;
					}
					account.deposit(input);
					break;
				} catch (NumberFormatException e) {
					System.out.println("올바른 숫자를 입력해주세요.");
				}
			}
		}
	}

	private boolean handleFixedDeposit(FixedDepositAccount account) {
		while (true) {
			try {
				int year = inputHandler.getIntInputWithQuestion(
					"예치 개월 수를 입력하세요? (1 ~ %d개월) ".formatted(account.getMaxYear()));
				if (year == 0) {
					return false;
				}
				if (year > account.getMaxYear()) {
					throw new NumberFormatException();
				}
				while (true) {
					String b = inputHandler.getInputWithQuestion(
						"%d개월(적용금리 %s%%)로 만기 처리하시겠어요? (y/n) ".formatted(year, account.getRateOfMaxYear(year)));
					if (b == null)
						return false;
					if (b.equalsIgnoreCase("Y")) {
						Account to = sendMoneyWhere(account);
						if (to == null) {
							continue;
						}
						account.finishAccount(account.getRateOfMaxYear(year), to);
						System.out.println(account.getAccountName() + " 통장은 해지되었습니다. 감사합니다.");
						accountService.getAccountList().remove(account);
						return true;
					} else if (b.equalsIgnoreCase("N")) {
						break;
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
			try {
				int input = inputHandler.getIntInputWithQuestion("출금 하실 금액은? ");
				if (input == 0) {
					return;
				}
				((Transactable)account).withdraw(input);
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

		while (true) {
			Account to = sendMoneyWhere(account);
			if (to == null) {
				return;
			}
			while (true) {
				try {
					int input = inputHandler.getIntInputWithQuestion("%s 통장에 보낼 금액은? ".formatted(to.getAccountName()));
					if (input == 0) {
						return;
					}
					((Transactable)account).transfer(to, input);
					return;
				} catch (LowBalanceException e) {
					System.out.print(e.getMessage());
					System.out.printf(" (잔액: %,d원)\n", account.getBalance());
				} catch (NumberFormatException e) {
					System.out.println("올바른 숫자를 입력해주세요.");
				}
			}
		}
	}

	private Account sendMoneyWhere(Account account) {
		try {
			int input = inputHandler.getIntInputWithQuestion("어디로 보낼까요? (" + accountService.getAccountList()
				.stream()
				.filter(acc -> acc.getAccountNo() != account.getAccountNo())
				.map(acc -> acc.getAccountNo() + ": " + acc.getAccountName())
				.reduce((a, b) -> a + " " + b)
				.orElse("") + ") ");
			if (input == 0) return null;

			return accountService.getAccountList()
				.stream()
				.filter(acc -> acc.getAccountNo() == input)
				.findFirst()
				.orElseThrow(NumberFormatException::new);
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
	}
}
