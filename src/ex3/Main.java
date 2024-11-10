package ex3;

import ex3.Bank;

public class Main {
	public static void main(String[] args) {
		Bank bank = new Bank();
		bank.start();
		bank.execute();
		bank.end();
	}
}
