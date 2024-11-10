package ex3;

import java.util.Scanner;

public class Bank {
	private Scanner scanner;
	private final User user = new User(
		new Account[] {
			new BasicAccount(1, "자유입출금", "홍길동", 0),
			new FixedDepositAccount(2, "정기예금", "홍길동", 50000000,
				new String[][] {{"1", "3.0"}, {"3", "3.35"}, {"6", "3.4"}, {"9", "3.35"}, {"12", "3.35"}, {"24", "2.9"},
					{"36", "2.9"}, {"48", "2.9"}}, 60),
			new MinusAccount(3, "마이너스", "홍길동", 0)
		});

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

	public void execute() {
		while (true) {
			System.out.print(">> 통장을 선택하세요(");
			user.getAccountList()
				.stream()
				.map(account -> account.getAccountNo() + ": " + account.getAccountName() + (
					(user.getAccountList().size() == account.getAccountNo()) ? "" : " "))
				.forEach(System.out::print);
			System.out.print(") ");
			String input = scanner.nextLine();
			if (input.equals("0") | input.equals("")) {
				System.out.println("금일 OneHanaBank는 업무를 종료합니다. 감사합니다.");
				break;
			}

			Account account = selectAccount(Integer.parseInt(input));
			System.out.println(account);
			accountMenu(account);
		}
	}

	private void accountMenu(Account account) {
		First:
		while (true) {
			System.out.printf("> %s (+: %s, -: 출금, T: 이체, I: 정보) ",
				(account instanceof FixedDepositAccount) ? "정기 예금이 만기되었습니다." : "원하시는 업무는?",
				(account instanceof FixedDepositAccount) ? "만기처리" : "입금");
			String tmp = scanner.nextLine();
			switch (tmp) {
				case "+" -> {
					if (account instanceof FixedDepositAccount) {
						while (true) {
							System.out.printf("예치 개월 수를 입력하세요? (1 ~ %d개월) ", ((FixedDepositAccount)account).getMaxYear());
							String y = scanner.nextLine();
							int year = 0;
							if (y.equals("") | y.equals("0")) break;
							else
								year = Integer.parseInt(y);
							System.out.printf("%d개월(적용금리 %s%%)로 만기 처리하시겠어요? (y/n) ", year,
								((FixedDepositAccount)account).getRateOfMaxYear(year));
							String b = scanner.nextLine();
							if (b.equals("Y") | b.equals("y")) {
								System.out.print("어디로 보낼까요? (");
								user.getAccountList()
									.stream()
									.filter(acc -> acc.getAccountNo() != account.getAccountNo())
									.map(acc -> acc.getAccountNo() + ": " + acc.getAccountName() + (
										(user.getAccountList().size() == acc.getAccountNo()) ? "" : " "))
									.forEach(System.out::print);
								System.out.print(") ");
								int num = Integer.parseInt(scanner.nextLine());
								Account to = user.getAccountList()
									.stream()
									.filter(acc -> acc.getAccountNo() == num)
									.findFirst()
									.get();
								((FixedDepositAccount)account).finishAccount(((FixedDepositAccount)account).getRateOfMaxYear(year), to);
								break First;
							} else if (b.equals("") | b.equals("0")) {
								break;
							}
						}
					} else {
						System.out.print("입금 하실 금액은? ");
						int amount = Integer.parseInt(scanner.nextLine());
						account.deposit(amount);
					}
				}
				case "-" -> {
					if (!(account instanceof Transactable)) {
						System.out.println("출금할 수 없는 통장입니다.");
					} else {
						System.out.print("출금 하실 금액은? ");
						int amount = Integer.parseInt(scanner.nextLine());
						((Transactable)account).withdraw(amount);
					}
				}
				case "T" -> {
					if (!(account instanceof Transactable)) {
						System.out.println("이체할 수 없는 통장입니다.");
					} else {
						System.out.print("어디로 보낼까요? (");
						user.getAccountList()
							.stream()
							.filter(acc -> acc.getAccountNo() != account.getAccountNo())
							.map(acc -> acc.getAccountNo() + ": " + acc.getAccountName() + (
								(user.getAccountList().size() == acc.getAccountNo()) ? "" : " "))
							.forEach(System.out::print);
						System.out.print(") ");
						int num = Integer.parseInt(scanner.nextLine());
						Account to = user.getAccountList()
							.stream()
							.filter(acc -> acc.getAccountNo() == num)
							.findFirst()
							.get();
						System.out.printf("%s 통장에 보낼 금액은? ", to);
						int amount = Integer.parseInt(scanner.nextLine());
						((Transactable)account).transfer(to, amount);
					}
				}
				case "I" -> {
					System.out.println(account);
					if (account instanceof FixedDepositAccount) {
						((FixedDepositAccount)account).printRates();
					}
				}
				case "", "0" -> {
					return;
				}
				default -> System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
			}
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
