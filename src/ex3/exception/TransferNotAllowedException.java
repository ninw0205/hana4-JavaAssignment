package ex3.exception;

public class TransferNotAllowedException extends AccountException {
	public TransferNotAllowedException() {
		super("이체할 수 없는 통장입니다.");
	}
}
