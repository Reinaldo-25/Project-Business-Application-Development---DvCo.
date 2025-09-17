package modelData;

public class User {
	private String id;
	private String username;
	private String email;
	private String password;
	private int age;
	private String gender;
	private String country;
	private String phoneNumber;
	private String role;

	public User(String id, String username, String email, String password, int age, String gender, String country, String phoneNumber,
			String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
