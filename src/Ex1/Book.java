package Ex1;

import java.util.Date;

public class Book {
	private String isbn;
	private String name;
	private int price;
	private String writer;
	private String description;
	private String category;
	private Date createDate;

	public Book(String isbn, String name, int price, String writer, String description, String category,
		Date createDate) {
		this.isbn = isbn;
		this.name = name;
		this.price = price;
		this.writer = writer;
		this.description = description;
		this.category = category;
		this.createDate = createDate;
	}
}
