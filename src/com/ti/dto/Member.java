package com.ti.dto;

import java.util.Arrays;

public class Member {

	private String id;
	private String pwd;
	private String name;
	private String gender;
	private String birthday;
	private String email;
	private String zipcode;
	private String address1;
	private String address2;
	private String hobby[];
	private String job;
	private Boolean enabled;

	public Member() {
		super();
	}

	public Member(String id, String pwd, String name, String gender, String birthday, String email, String zipcode,
			String address1, String address2, String[] hobby, String job) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.zipcode = zipcode;
		this.address1 = address1;
		this.address2 = address2;
		this.hobby = hobby;
		this.job = job;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String[] getHobby() {
		return hobby;
	}

	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}	


	@Override
	public String toString() {
		return "Member [id=" + id + ", pwd=" + pwd + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday
				+ ", email=" + email + ", zipcode=" + zipcode + ", address1=" + address1 + ", address2=" + address2
				+ ", hobby=" + Arrays.toString(hobby) + ", job=" + job + ", enabled=" + enabled + "]";
	}
}