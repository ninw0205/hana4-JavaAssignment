package ex1;

public class User {
	private final String name;
	private final String phoneNo;

	public User(String name, String phoneNo) {
		this.name = name;
		this.phoneNo = phoneNo;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	@Override
	public String toString() {
		return "이름 " + getName() +
			", 연락처 " + getPhoneNo();
	}
}
