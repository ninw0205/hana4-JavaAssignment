package ex3.exception;

public class LowBalanceException extends AccountException {

	public LowBalanceException() {
		super("잔액이 부족합니다!");
	}
}
