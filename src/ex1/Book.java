package ex1;

public class Book {
	private final String isbn;
	private final String name;
	private final int price;
	private final String writer;
	private final String description;
	private final String category;
	private final String createDate;

	public Book(String isbn, String name, int price, String writer, String description, String category,
		String createDate) {
		this.isbn = isbn;
		this.name = name;
		this.price = price;
		this.writer = writer;
		this.description = description;
		this.category = category;
		this.createDate = createDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getWriter() {
		return writer;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getCreateDate() {
		return createDate;
	}

	@Override
	public String toString() {
		return getIsbn() + '|' + getName() + '|' + getPrice() + "원|" + getWriter() + '|' + getDescription() + '|'
			+ getCategory() + '|' + getCreateDate();
	}
}
