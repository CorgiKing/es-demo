package org.corgiking.resthighclient;

public class People {
	private String name;
	private Integer age;
	private String sex;
	private String info;

	public People(String name, Integer age, String sex, String info) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "People [name=" + name + ", age=" + age + ", sex=" + sex + ", info=" + info + "]";
	}

}
