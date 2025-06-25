package entities;

import exceptions.StringNotValidException;

import java.util.Objects;

public class User {
	private String name;
	private String surname;
	private int age;

	public User(String name, String surname, int age) {
		this.name = name;
		this.surname = surname;
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws StringNotValidException {
		if (name.length() < 3) throw new StringNotValidException(name);
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", age=" + age +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
	}


}
