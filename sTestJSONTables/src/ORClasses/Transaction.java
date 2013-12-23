package ORClasses;

import java.util.ArrayList;

public class Transaction {
	String storeName;
	ArrayList<String> category;

	public Transaction()  {
		// TODO Auto-generated constructor stub

	}
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void setCategory(ArrayList<String> category) {
		this.category = category;
	}
}
