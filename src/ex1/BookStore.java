package ex1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookStore {
	private Scanner scanner;
	private User user;
	private final ArrayList<Book> books = new ArrayList<>();
	private Map<Book, Integer> cart = new HashMap<>();

	public User getUser() {
		return user;
	}

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

	public void init() {
		books.add(
			new Book("ISBN1234", "셜록홈즈", 20000, "코난 도일", "그 누구도 뛰어넘지 못했던 추리 소설의 고전", "추리소설", "2018/10/08"));
		books.add(
			new Book("ISBN2345", "도리안 그레이의 초상", 16000, "오스카 와일드", "예술을 위한 예술!", "고전소설", "2022/01/22"));
		books.add(
			new Book("ISBN3456", "쥐덫", 27000, "애거서크리스티", "폭설 속에 갇힌 몽스웰 여관 - 네 명의 손님과 주인 부부, 그리고 한 명의 형사", "추리소설", "2019/06/10"));
	}

	public void login() {
		System.out.print("당신의 이름을 입력하세요 :");
		String name = scanner.nextLine();
		System.out.print("연락처를 입력하세요 :");
		String phoneNo = scanner.nextLine();
		this.user = new User(name, phoneNo);
	}

	public int printMenu() {
		System.out.println("*****************************************************");
		System.out.println("오늘의 선택, 코난문고");
		System.out.println("영원한 스테디셀러, 명탐정 코난시리즈를 만나보세요~");
		System.out.println("*****************************************************");
		System.out.println("1. 고객 정보 확인하기 2. 장바구니 상품 목록 보기");
		System.out.println("3. 바구니에 항목 추가하기 4. 장바구니의 항목 삭제하기");
		System.out.println("5. 장바구니 비우기 6. 영수증 표시하기 7. 종료");
		System.out.println("*****************************************************");
		System.out.print("메뉴 번호를 선택해주세요 ");

		return Integer.parseInt(scanner.nextLine());
	}

	public void addBook() {
		books.forEach(System.out::println);
		System.out.print("장바구니에 추가할 도서의 ID를 입력하세요 :");
		String bookId = this.scanner.nextLine();
		System.out.print("장바구니에 추가하시겠습니까? Y|N ");
		String input = scanner.nextLine();
		if (input.equals("Y") | input.equals("Y".toLowerCase())) {
			Book book = books.stream().filter(book1 -> book1.getIsbn().equals(bookId)).findAny().get();

			if (cart.containsKey(book)) {
				cart.replace(book, cart.get(book) + 1);
			} else
				cart.put(book, 1);
		}
	}

	public void deleteBook() {
		System.out.print("장바구니에서 삭제할 도서의 ID를 입력하세요 : ");
		String bookId = this.scanner.nextLine();
		Book book = books.stream().filter(book1 -> book1.getIsbn().equals(bookId)).findAny().get();

		if (cart.containsKey(book)) {
			cart.replace(book, cart.get(book) - 1);
			System.out.println("장바구니에서 " + bookId + "가 삭제되었습니다");
		}

		for (Book b : cart.keySet()) {
			if (cart.get(b) == 0) {
				cart.remove(b);
			}
		}
	}

	public void resetCart() {
		cart = new HashMap<>();
	}

	public void printReceipt() {
		if (cart.isEmpty()) {
			System.out.println("장바구니가 비었습니다");
			return;
		}

		System.out.print("배송받을 분은 고객정보와 같습니까? ");
		String input = scanner.nextLine();
		if (!input.equals("Y") & !input.equals("Y".toLowerCase())) {
			return;
		}

		System.out.print("배송지를 입력해주세요 ");
		String addr = scanner.nextLine();
		System.out.println("--------------------배송 받을 고객 정보--------------------");
		System.out.printf("고객명: %s 연락처: %s%n", getUser().getName(), getUser().getPhoneNo());
		System.out.printf("배송지: %s 발송일: %s%n", addr, new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		this.printCart();
	}

	public void printCart() {
		System.out.println("장바구니 상품 목록 :");
		System.out.println("*****************************************************");
		System.out.println("도서ID	|	수량		|	합계");
		for (Book b : cart.keySet()) {
			System.out.println(b.getIsbn() + "|" + cart.get(b) + "			|"+ cart.get(b) * b.getPrice() + "원");
		}
		System.out.println("*****************************************************");
		System.out.println();
	}
}
