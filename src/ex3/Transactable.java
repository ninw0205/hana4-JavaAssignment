package ex3;

import ex3.exception.AccountException;

public interface Transactable {
	void withdraw(int amount) throws AccountException;
	void transfer(Account account, int amount) throws AccountException;
}
