package com.bank.sql;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class BankApp {

	private static Scanner sc = new Scanner(System.in);

	private static User u = null;

	static float transfer = 0;

	final static DBAccessor dao = DBAccessor.getInstance();

	public static void main(String[] args) throws Exception {

		MyLoop: while (true) { // input loop

			System.out.println("<----------------------------------------------------->");
			System.out.println("             Welcome to Stevo's Banking                  ");
			System.out.println("               	 Steven's Bank App                     ");
			System.out.println("<----------------------------------------------------->\n");

			System.out.println("1) Log In as a User, Employee, or Admin");

			System.out.println("2) Customer Account Registration");

			System.out.println("3) Employee Account Registration/Log In");

			System.out.println("0) Exit Program\n");

			System.out.println("Enter Your Option: ");

			int option = sc.nextInt();

			sc.nextLine();

			switch (option) {

			case 1:

				login();
				break;

			case 2:

				createCustomer();
				break;

			case 3:

				createEmployee();
				break;

			case 0: 
				System.out.println("Thank you for using Stevo's Bank! :D");
				break MyLoop;

			}

			if (u != null) {

				loggedIn();

			}

		}

		sc.close();

	}

	public static void loggedIn() {

		if (!u.approved) {

			System.out.println(u.name + " are not approved for Deposits/Withdrawal/Transfer by an Admin. User Logged Out...\n");

			u = null;

			return;

		}

		while (true) {
			if (!u.employee && !u.admin) {
				System.out.println("Choose From These Numbers:");
				System.out.println("1) Deposit Into");
				System.out.println("2) Withdraw From");
				System.out.println("3) Transfer From");
			}

			if (u.admin) {
				System.out.println("Choose From These Numbers:");
				System.out.println("1) Do you want to edit a Customer's Depositing? ");
				System.out.println("2) Do you want to edit a Customer's Withdrawing?");
				System.out.println("3) Do you want to edit a Customer's Transferring?");
				System.out.println("4) Would you like to Approve a User/Customer?");
				System.out.println("5) Would you like to Delete a Customer?");

			}

			if (u.employee) {

				Map<String, User> m = dao.getAllUsers();

				System.out.println("\n4) Would you like to Approve a User?");

			}

			System.out.println("0) Log Out of: " + u.name);

			System.out.println("Enter Your Option: ");

			int option = sc.nextInt();

			sc.nextLine();

			if (!u.admin && option > 4) {

				System.out.println("The option that you inputed is Invalid. 0-4 Only!");

				continue;

			}

			switch (option) {

			// log out

			case 0:

				System.out.println(u.name + " Successfully Logged Out\n");

				u = null;

				return;

			case 1:

				switch (option) {

				case 1:
					if (u.admin) {
						Map<String, User> m = dao.getAllUsers();

						System.out.println("Type in the name that you want to edit: ");

						String name = sc.nextLine();

						User enteredUser = m.get(name);

						System.out.println("1) Checkings");
						System.out.println("2) Savings");

						option = sc.nextInt();

						if (option == 1) {

							depositCheckings(enteredUser);

						}

						if (option == 2) {

							depositSavings(enteredUser);

						}

					}

					if (!u.admin) {
						System.out.println("1) Checkings");
						System.out.println("2) Savings");
						option = sc.nextInt();

						if (option == 1) {

							depositCheckings();

						}
						if (option == 2) {
							depositSavings();
						}
						break;
					}

				}
				break;

			case 2:

				switch (option) {

				case 2:
					if (u.admin) {
						Map<String, User> m = dao.getAllUsers();

						System.out.println("Type in name that you want to edit: ");

						String name = sc.nextLine();

						User enteredUser = m.get(name);

						System.out.println("1) Checkings");
						System.out.println("2) Savings");

						option = sc.nextInt();

						if (option == 1) {

							withdrawCheckings(enteredUser);

						}

						if (option == 2) {

							withdrawSavings(enteredUser);

						}

					}
					if (!u.admin) {
						System.out.println("1) Checkings");
						System.out.println("2) Savings");
						option = sc.nextInt();

						if (option == 1) {

							withdrawCheckings();

						}
						if (option == 2) {
							withdrawSavings();
						}
						break;
					}
					break;
				}
				break;

			case 3:

				switch (option) {

				case 3:
					if (u.admin) {
						Map<String, User> m = dao.getAllUsers();

						System.out.println("Who do you want to edit: ");

						String name = sc.nextLine();

						User enteredUser = m.get(name);

						TransferBalance(enteredUser);

					}
				}

				if (!u.admin) {
					TransferBalance();
					break;
				}

				break;

			case 4:
				approveUsers();
				break;

			case 5:
				deleteChoosenUser();
				break;

			}

		}

	}

	private static void deleteChoosenUser() {

		System.out.println("\nUsers in the system:");

		Map<String, User> m = dao.getAllUsers();

		for (String k : m.keySet()) {

			User t = m.get(k);

			System.out.println(k + ": " + t.approved);

		}

		System.out.println("\nDelete a Customer: ");
		String name = sc.nextLine();
		User enteredUser = m.get(name);
		enteredUser.approved = true;
		dao.deleteUser(enteredUser);
		System.out.println(enteredUser.name + " has been deleted.");

	}

	public static void login() throws IOException {

		System.out.println("\nLog in...");

		boolean authenticated = false;

		while (!authenticated) {

			System.out.println("\nEnter Your Username: ");

			String name = sc.nextLine();

			u = dao.getUser(name);

			if (u == null) {

				System.out.println("Invalid Username: " + name);

				continue;

			}

			System.out.println("Enter your password: ");

			String password = sc.nextLine();

			if (!u.password.equals(password)) {

				System.out.println("Invalid Password for: " + name);

			} else

				authenticated = true;

		}
		System.out.println("\nWelcome " + u.name + " To Stevo's Bank!\n");
		loggedIn();

	}

	public static void createCustomer() {

		System.out.println("Customer Account Creation");

		createUser(false, false, false);

		System.out.println("Customer Account has been created. Customer Username is: " + u.name);

	}

	public static void createEmployee() {

		System.out.println("Employee Account Creation\n");

		createUser(false, true, true);

		System.out.println("Employee Account has been created. Employee Username is: " + u.name);

	}

	public static void createUser(boolean admin, boolean employee, boolean approved) {

		String name = null;

		while (true) {

			System.out.println("Enter a new username: ");

			name = sc.nextLine();

			if (dao.getUser(name) == null)

				break;

			System.out.println("User name already exists!");

		}

		System.out.println("Enter a new password: ");

		String password = sc.nextLine();

		u = new User(name, password, 0, 0, admin, employee, approved); // logs in

		boolean inserted = dao.insertUser(u);

		if (inserted) {

		} else {

		}

	}

	private static void approveUsers() {

		System.out.println("\nListed below are the Users:\n");

		Map<String, User> m = dao.getAllUsers();

		if (m.isEmpty()) {

			System.out.println("There are no users in the system at the moment.");

			return;

		}

		System.out.println("\nApprove a User: ");

		String name = sc.nextLine();

		User enteredUser = m.get(name);

		if (enteredUser.approved) {
			System.out.println("\n" + enteredUser.name + " is already approved.\n");
		} else
			enteredUser.approved = true;

		dao.updateUser(enteredUser);

		System.out.println(enteredUser.name + " has been approved.\n");

	}

	public static void depositCheckings() {

		System.out.println("Your Current Checking Balance is : $" + u.balance_checkings);

		System.out.println("Deposit an amount: ");

		System.out.print("$");

		float depositCheckings = sc.nextFloat();

		u.depositCheckings(depositCheckings);

		if (depositCheckings <= 0) {
			System.out.println("Can't be a negative number or 0!");
			
			return;
		}

		dao.Transaction(u, depositCheckings, u.balance_checkings);

		System.out.println("Your New Checking Balance is : $" + u.balance_checkings);

	}

	public static void depositCheckings(User name) {

		System.out.println(name.name + " Your Current Checkings Balance is : $" + name.balance_checkings);

		System.out.println("Deposit an amount: ");

		System.out.print("$");

		float depositCheckings = sc.nextFloat();

		name.depositCheckings(depositCheckings);

		if (depositCheckings <= 0) {
			System.out.println("Can't be a negative number or 0!");
			return;
		} 

			dao.Transaction(name, depositCheckings, name.balance_checkings);

		System.out.println(name.name + "Your New Checkings Balance is : $" + name.balance_checkings);

	}

	public static void withdrawCheckings() {

		System.out.println("Your Current Checkings Balance is : $" + u.balance_checkings);

		System.out.println("Deposit an amount: ");

		System.out.print("$");

		float withdrawalCheckings = sc.nextFloat();

		if (withdrawalCheckings > u.balance_checkings) {

			System.out.println("\nOverdrafting! Must be less than your current balance.\n");

			return;
		}

		u.withdrawCheckings(withdrawalCheckings);

		dao.Transaction(u, withdrawalCheckings, u.balance_checkings);

		System.out.println("Your New Checkings Balance is : $" + u.balance_checkings);

	}

	public static void withdrawCheckings(User name) {

		System.out.println(name.name + " Your Current Checkings Balance is : $" + name.balance_checkings);

		System.out.println("Withdraw an amount: ");

		System.out.print("$");

		float withdrawalCheckings = sc.nextFloat();

		name.withdrawCheckings(withdrawalCheckings);

		if (withdrawalCheckings > name.balance_checkings) {

			System.out.println("\nOverdrafting! Must be less than the current balance.\n");

			return;
		}

		dao.Transaction(name, withdrawalCheckings, name.balance_checkings);

		System.out.println(name.name + " Your New Checkings Balance is : $" + name.balance_checkings);

	}

	public static void depositSavings(User name) {

		System.out.println(name.name + " Your Current Savings Balance is : $" + name.balance_savings);

		System.out.println("Deposit an amount: ");

		System.out.print("$");

		float depositSavings = sc.nextFloat();

		name.depositSavings(depositSavings);

		if (depositSavings <= 0) {
			System.out.println("Can't be a negative number or 0!");
			return;
		}

		dao.Transaction(name, depositSavings, name.balance_savings);

		System.out.println(name.name + " Your New Savings Balance is : $" + name.balance_savings);

	}

	public static void depositSavings() {

		System.out.println("Your Current Savings Balance is : $" + u.balance_savings);

		System.out.println("Deposit an amount: ");

		System.out.print("$");

		float depositSavings = sc.nextFloat();

		u.depositSavings(depositSavings);

		if (depositSavings <= 0) {
			System.out.println("Can't be a negative number!");
			return;
		}

		dao.Transaction(u, depositSavings, u.balance_savings);

		System.out.println("Your New Savings Balance is : $" + u.balance_savings);

	}

	public static void withdrawSavings() {

		System.out.println("Your Current Savings Balance is : $" + u.balance_savings);

		System.out.println("Withdraw an amount: ");

		System.out.print("$");

		float withdrawalSavings = sc.nextFloat();

		u.withdrawSavings(withdrawalSavings);

		if (withdrawalSavings > u.balance_savings) {

			System.out.println("\nOverdrafting! Must be less than the current balance.\n");

			return;
		}

		dao.Transaction(u, withdrawalSavings, u.balance_savings);

		System.out.println("Your New Savings Balance is : $" + u.balance_savings);

	}

	public static void withdrawSavings(User name) {

		System.out.println(name.name + " Your Current Savings Balance is : $" + name.balance_savings);

		System.out.println("Withdraw an amount: ");

		System.out.print("$");

		float withdrawalSavings = sc.nextFloat();

		name.withdrawSavings(withdrawalSavings);

		if (withdrawalSavings > name.balance_checkings) {

			System.out.println("\nOverdrafting! Must be less than the current balance.\n");

			return;
		}

		dao.Transaction(name, withdrawalSavings, name.balance_savings);

		System.out.println(name.name + " Your New Savings Balance is : $" + name.balance_savings);

	}

	public static void TransferBalance() {
		System.out.println("Available balance in checkings account: " + u.getBalanceCheckings());
		System.out.println("Available balance in savings account: " + u.getBalanceSavings());
		System.out.println("Enter the amount you want to transfer: ");
		float transferBalance = sc.nextFloat();
		System.out.println("\n1) Transfer from checkings to savings");
		System.out.println("2) Transfer from savings to checkings\n");
		int option = sc.nextInt();

		switch (option) {

		case 1:

			if (u.getBalanceCheckings() >= transferBalance) {

				u.withdrawCheckings(transferBalance);

				dao.Transaction(u, transferBalance, u.balance_checkings);

				System.out.println(u.name + "'s new Checkings Balance is : $" + u.balance_checkings);

				u.depositSavings(transferBalance);

				dao.Transaction(u, transferBalance, u.balance_savings);

				System.out.println(u.name + "'s new Savings Balance is : $" + u.balance_savings);

			}
			break;

		case 2:

			if (u.getBalanceSavings() > transferBalance) {

				u.withdrawSavings(transferBalance);

				dao.Transaction(u, transferBalance, u.balance_savings);

				System.out.println(u.name + "'s new Savings Balance is : $" + u.balance_savings);

				u.depositCheckings(transferBalance);

				dao.Transaction(u, transferBalance, u.balance_checkings);

				System.out.println(u.name + "'s new Checkings Balance is : $" + u.balance_checkings);
			}
			break;

		}
	}

	public static void TransferBalance(User name) {
		System.out.println("Available balance in checkings account: " + name.getBalanceCheckings());
		System.out.println("Available balance in savings account: " + name.getBalanceSavings());
		System.out.println("Transfer an amount: ");
		float transferBalance = sc.nextFloat();
		System.out.println("\n1) Checkings to Savings");
		System.out.println("2) Savings to Checkings\n");
		int option = sc.nextInt();

		switch (option) {

		case 1:

			if (name.getBalanceCheckings() >= transferBalance) {

				name.withdrawCheckings(transferBalance);

				dao.Transaction(name, transferBalance, name.balance_checkings);

				System.out.println(name.name + "'s new Checkings Balance is : $" + name.balance_checkings);

				name.depositSavings(transferBalance);

				dao.Transaction(name, transferBalance, name.balance_savings);

				System.out.println(name.name + "'s new Savings Balance is : $" + name.balance_savings);

			}
			break;

		case 2:

			if (name.getBalanceSavings() > transferBalance) {

				name.withdrawSavings(transferBalance);

				dao.Transaction(name, transferBalance, name.balance_savings);

				System.out.println(name.name + "'s new Savings Balance is : $" + name.balance_savings);

				name.depositCheckings(transferBalance);

				dao.Transaction(name, transferBalance, name.balance_checkings);

				System.out.println(name.name + "'s new Checkings Balance is : $" + name.balance_checkings);
			}
			break;

		}
	}

	// @Override

	protected void finalize() throws Throwable {

		super.finalize();

	}
}
