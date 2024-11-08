package Ex1;

public class Main {
	public static void main(String[] args) {
		BookStore bookStore = new BookStore();
		bookStore.init();
		bookStore.startScan();
		bookStore.createUser();
		int num = bookStore.printMenu();
		switch (num) {
			case 1 -> {
				System.out.println("현재 고객 정보:");
				System.out.println(bookStore.getUser());
			}
		}

		bookStore.endScan();
	}
}
