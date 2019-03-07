package com.bank.sql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// bean with hash and toString()
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5539802788837931219L;
	public String name;
	public String password = "ttt";
	public float balance_checkings = 0;
	public float balance_savings = 0;
	public boolean admin = false;
	public boolean employee = false;
	public boolean approved = false;
	public boolean deleted = false;
	public static Map<String, User> users = new HashMap<>(2); // persistent storage :P

	public User(String name) {
		this.name = name;
		users.put(name, this);
	}

	public User(String name, String password, float balance_checkings, float balance_savings, boolean admin, boolean employee,
			boolean approved) {
		this.name = name;
		this.password = password;
		this.balance_checkings = balance_checkings;
		this.balance_savings = balance_savings;
		this.admin = admin;
		this.employee = employee;
		this.approved = approved;
		users.put(name, this);
	}
	
	public User() {
		
	}

	public void depositCheckings(float deposit) {
		this.balance_checkings += deposit;

	}

	public void withdrawCheckings(float withdrawal) {
		this.balance_checkings -= withdrawal;

	}

	public void depositSavings(float deposit) {
		this.balance_savings += deposit;
	}

	public void withdrawSavings(float withdrawal) {
		this.balance_savings -= withdrawal;
	}


	public float getBalanceCheckings() {

		return this.balance_checkings;
	}

	public float getBalanceSavings() {

		return balance_savings;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", Checkings= " + balance_checkings + ", Savings= "
				+ balance_savings + ", admin=" + admin + ", employee=" + employee + ", approved=" + approved + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + (employee ? 1231 : 1237);
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(balance_checkings);
		result = prime * result + Float.floatToIntBits(balance_savings);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (admin != other.admin)
			return false;
		if (employee != other.employee)
			return false;
		if (approved != other.approved)
			return false;
		if (Float.floatToIntBits(balance_checkings) != Float.floatToIntBits(other.balance_checkings))
			return false;
		if (Float.floatToIntBits(balance_savings) != Float.floatToIntBits(other.balance_savings))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
