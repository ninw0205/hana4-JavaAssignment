package ex3;

public interface Transactable {
	void withdraw(int amount);
	void transfer(Account account, int amount);
}
