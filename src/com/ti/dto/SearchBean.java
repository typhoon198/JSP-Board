package com.ti.dto;

public class SearchBean {
	private String keyField; 
	private String keyWord;
	public SearchBean() {
		super();
	}
	public SearchBean(String keyField, String keyWord) {
		super();
		this.keyField = keyField;
		this.keyWord = keyWord;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	@Override
	public String toString() {
		return "SearchBean [keyField=" + keyField + ", keyWord=" + keyWord + "]";
	}
	

}
