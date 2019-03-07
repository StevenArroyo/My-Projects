package com.bank.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBAccessor implements DBAccess {
	private static DBAccessor instance;

	private DBAccessor() {
	}

	public static DBAccessor getInstance() {
		if (instance == null) {
			instance = new DBAccessor();
		}
		return instance;
	}

	public User getUser(String name) {
		try (Connection con = ConnectionUtil.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT name, password, balance_checkings, balance_savings, admin, employee, approved "
							+ "FROM user_account WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new User(rs.getString("name"), rs.getString("password"), rs.getFloat("balance_checkings"),
						rs.getFloat("balance_savings"), rs.getBoolean("admin"), rs.getBoolean("employee"), rs.getBoolean("approved"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		// BankApp.logger.debug("No user found (Result set empty)");
		return null;

	}

	public boolean insertUser(User u) {
		/*try (Connection con = ConnectionUtil.getConnection()) {
			int idx = 0;
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO user_account (name, password, balance_checkings, balance_savings, admin, employee, approved) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(++idx, u.name);
			ps.setString(++idx, u.password);
			ps.setFloat(++idx, u.balance_checkings);
			ps.setFloat(++idx, u.balance_savings);
			ps.setBoolean(++idx, u.admin);
			ps.setBoolean(++idx, u.employee);
			ps.setBoolean(++idx, u.approved);

			// BankApp.logger.trace("executing INSERT...");
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.print(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		// BankApp.logger.debug("INSERT user failed: " + u);
		return false;*/
		
		try (Connection con = ConnectionUtil.getConnection()) {
			int idx = 0;
			String sql = "{ call add_user(?, ?, ?, ?, ? ,?, ?) }";
			
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setString(++idx, u.name);
			cs.setString(++idx, u.password);
			cs.setFloat(++idx, u.balance_checkings);
			cs.setFloat(++idx, u.balance_savings);
			cs.setBoolean(++idx, u.admin);
			cs.setBoolean(++idx, u.employee);
			cs.setBoolean(++idx, u.approved);
			
			return cs.executeUpdate() > 0;
		} catch (SQLException s ) {
			System.err.print(s.getMessage());
			System.err.println("SQL State: " + s.getSQLState());
			System.err.println("Error Code: " + s.getErrorCode());
		}
		return false;
	}

	public boolean deleteUser(User u) {
		// DELETE FROM user_account WHERE name = 'Ian B';
		try (Connection con = ConnectionUtil.getConnection()) {
			int idx = 0;
			PreparedStatement ps = con.prepareStatement("DELETE FROM user_account WHERE name = ?");
			ps.setString(++idx, u.name);
			
			// BankApp.logger.trace("executing DELETE...");
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		// BankApp.logger.debug("DELETE user failed: " + u);
		return false;
	}

	public boolean updateUser(User u) {
		try (Connection con = ConnectionUtil.getConnection()) {
			int idx = 0;
			PreparedStatement ps = con.prepareStatement("UPDATE user_account SET "
					+ "balance_checkings = ?, balance_savings = ?, approved = ?, admin = ?, employee = ? WHERE name = ?");
			ps.setFloat(++idx, u.balance_checkings);
			ps.setFloat(++idx, u.balance_savings);
			ps.setBoolean(++idx, u.approved);
			ps.setBoolean(++idx, u.admin);
			ps.setBoolean(++idx, u.employee);
			ps.setString(++idx, u.name);

			// BankApp.logger.trace("executing UPDATE to User..." + u);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		// BankApp.logger.debug("UPDATE user modified 0 rows: " + u);
		return false;
	}

	@Override
	public Map<String, User> getAllUsers() {
		Map<String, User> um = new HashMap<>();
		try (Connection con = ConnectionUtil.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement("SELECT name, password, balance_checkings, balance_savings, admin, employee, approved "
							+ "FROM user_account");
			// BankApp.logger.trace("getAllUsers query executing...");
			ResultSet rs = ps.executeQuery();
			// BankApp.logger.trace("query done.");
			while (rs.next()) {
				User u = new User(rs.getString("name"), rs.getString("password"), rs.getFloat("balance_checkings"),
						rs.getFloat("balance_savings"), rs.getBoolean("admin"),rs.getBoolean("employee"), rs.getBoolean("approved"));
				um.put(u.name, u);
				System.out.println("User: " + u.name + "  Checkings: " + u.balance_checkings + "  Savings: " + u.balance_savings+ "  Approved: " + u.approved);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}

		// BankApp.logger.debug("getAll elements in map: " + um.size());
		return um;
	}

	public boolean Transaction(User u, float balance_checkings, float balance_savings) {
		try (Connection con = ConnectionUtil.getConnection()) {
			int idx = 0;
			PreparedStatement ps = con.prepareStatement("UPDATE user_account SET "
					+ "balance_checkings = ?, balance_savings = ?, approved = ?, admin = ?, employee = ? WHERE name = ?");
			ps.setFloat(++idx, u.balance_checkings);
			ps.setFloat(++idx, u.balance_savings);
			ps.setBoolean(++idx, u.approved);
			ps.setBoolean(++idx, u.admin);
			ps.setBoolean(++idx, u.employee);
			ps.setString(++idx, u.name);
	
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.err.println("SQL State: " + e.getSQLState());
			System.err.println("Error code: " + e.getErrorCode());
		}
		return false;
	}
	



}