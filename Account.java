import java.io.IOException;

/**
 * @author Ricardo Pena and Aaron Alarcon
 * PinkBlink
 * November 26, 2019
 * CS3331
 * Daniel Mejia
 * Programming Assignment 5
 * 
 * The code for this class is from Ricardo Pena
 * 
 * Create a customer which has checking/savings/credit accounts and simulate various transactions.
 * I confirm that the work of this assignment is completely my own.By turning in this assignment,
 * I declare that I did not receive unauthorized assistance. Moreover, all the deliverables 
 * including, but not limited to the source code, lab report and output files were written and 
 * produced by me alone.
 */

public abstract class Account {
	int acctNo;
	double balance;
	double interest;
	String type;
	
	//Constructors
	public Account(String acctNo, String balance) {
		this.acctNo =  Integer.parseInt(acctNo);
		this.balance = Double.parseDouble(balance);
	}
	
	public Account(int acctNo, double balance) {
		this.acctNo = acctNo;
		this.balance = balance;
		this.interest = 0;
	}

	//Getters and Setters	
	public int getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(int acctNo) {
		this.acctNo = acctNo;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
	/**
	 * This method returns the type of Account (Checking/Savings/Credit).
	 * @return Returns the account type (Checking/Savings/Credit).
	 */
	public String getType() {
		return type;
	}
	

	//Other methods
	/**
	 * This method displays the current balance of the account.
	 * @return Returns String value.
	 */
	public String showBalance() {
		return (type + " Account Balance: " + balance);
	}
	
	//Transaction Methods
	/**
	 * This method deposits a specified amount into the account.
	 * @param amount Amount being deposited.
	 * @return Returns True if deposit was completed. Returns False if deposit failed.
	 */
	public boolean deposit(double amount) {
		balance += amount;
		return true;
	}
	/**
	 * This method withdraws a specified amount from an account.
	 * @param amount Amount being withdrawn.
	 * @return Returns True if withdrawal was completed. Returns False if withdrawal failed.
	 */
	public boolean withdraw(double amount) {
		balance -= amount;
		return true;
	}

}