package Ex1;

public class Main {
	public static void main(String[] args) {
		BookStore bookStore = new BookStore();
		bookStore.init();
		bookStore.startScan();
		bookStore.login();

		LOOP:
		while (true) {
			int num = bookStore.printMenu();
			switch (num) {
				case 1 -> {
					System.out.println("현재 고객 정보:");
					System.out.println(bookStore.getUser());
				}
				case 2 -> bookStore.printCart();
				case 3 -> bookStore.addBook();
				case 4 -> bookStore.deleteBook();
				case 5 -> bookStore.resetCart();
				case 6 -> bookStore.printReceipt();
				case 7 -> {break LOOP;}
				default -> {
					System.out.println("잘못된 접근!");
				}
			}
		}
		bookStore.endScan();
	}
}
