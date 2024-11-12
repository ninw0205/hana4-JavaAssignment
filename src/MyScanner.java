import java.util.Scanner;

public class MyScanner {
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

	public String questionAndAnswer(String question) {
		System.out.println(question);

		return scanner.nextLine();
	}
}
