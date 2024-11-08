package Ex1;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BookStore {
	private Scanner scanner;
	private User user;

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

	public void login(String name, String phoneNo) {
		user = new User(name, phoneNo);
	}

	public void init() {
		ArrayList<Book> books = new ArrayList<>();
		books.add(
			new Book("ISBN1234", "셜록홈즈", 20000, "코난 도일", "그 누구도 뛰어넘지 못했던 추리 소설의 고전", "추리소설", new Date(2018 - 10 - 8)));
		books.add(
			new Book("ISBN2345", "도리안 그레이의 초상", 16000, "오스카 와일드", "예술을 위한 예술!", "고전소설", new Date(2022 - 1 - 22)));
		books.add(
			new Book("ISBN3456", "쥐덫", 27000, "애거서크리스티", "폭설 속에 갇힌 몽스웰 여관 - 네 명의 손님과 주인 부부, 그리고 한 명의 형사", "추리소설",
				new Date(2019 - 6 - 10)));
	}

	public void createUser() {
		System.out.print("당신의 이름을 입력하세요 :");
		String name = scanner.nextLine();
		System.out.print("연락처를 입력하세요 :");
		String phoneNo = scanner.nextLine();
		this.login(name, phoneNo);
	}

	public int printMenu() {
		System.out.println("	*****************************************************");
		System.out.println("	오늘의 선택, 코난문고");
		System.out.println("	영원한 스테디셀러, 명탐정 코난시리즈를 만나보세요~");
		System.out.println("	*****************************************************");
		System.out.println("	1. 고객 정보 확인하기 2. 장바구니 상품 목록 보기");
		System.out.println("	3. 바구니에 항목 추가하기 4. 장바구니의 항목 삭제하기");
		System.out.println("	5. 장바구니 비우기 6. 영수증 표시하기 7. 종료");
		System.out.println("	*****************************************************");
		System.out.println("메뉴 번호를 선택해주세요");
		int input = Integer.parseInt(scanner.nextLine());

		return input;
	}
}
