package components.entities;

public class User {

	private String userName;

	public User(String user) {
		this.userName = user;

	}

	public User() {
	}

	public String toString() {
		return userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
