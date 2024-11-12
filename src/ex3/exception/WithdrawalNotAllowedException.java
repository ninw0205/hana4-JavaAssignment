package ex3.exception;

public class WithdrawalNotAllowedException extends AccountException {
	public WithdrawalNotAllowedException() {
		super("출금할 수 없는 통장입니다.");
	}
}
