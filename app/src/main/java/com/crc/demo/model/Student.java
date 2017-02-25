package com.crc.demo.model;

public class Student {

	private String name;
	private String address;
	private String icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", address=" + address + ", icon="
				+ icon + "]";
	}

}
