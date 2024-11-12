package ex3;

import java.util.Scanner;

public class InputHandler {
	private Scanner scanner;

	public void startScan() {
		if (this.scanner == null) {
			scanner = new Scanner(System.in);
		}
	}

	public void endScan() {
		if (this.scanner != null) {
			scanner.close();
		}
	}

	public String getInputWithQuestion(String question) {
		System.out.print(question);
		String input = scanner.nextLine();
		if (input.equals("0") | input.equals("")) {
			return null;
		}
		return input;
	}

	public int getIntInputWithQuestion(String question) throws NumberFormatException {
		System.out.print(question);
		String input = scanner.nextLine();
		if (input.equals("0") | input.equals("")) {
			return 0;
		}
		return Integer.parseInt(input);
	}
}
