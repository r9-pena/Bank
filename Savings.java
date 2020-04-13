/**
 * @author Ricardo Pena and Aaron Alarcon
 * PinkBlink
 * November 8, 2019
 * CS3331
 * Daniel Mejia
 * Programming Assignment 5
 * 
 * The template for this class is from Ricardo Pena, except as noted for each method
 * 
 * Create a customer which has checking/savings/credit accounts and simulate various transactions.
 * 
 * I confirm that the work of this assignment is completely my own.By turning in this assignment,
 * I declare that I did not receive unauthorized assistance. Moreover, all the deliverables 
 * including, but not limited to the source code, lab report and output files were written and 
 * produced by me alone.
 */

public class Savings extends Account {
	
	private static int numberOfAccounts = 2000;
	
	final private static String type = "Savings"; 
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 */
	public Savings(String acctNo, String balance) {
		super(acctNo, balance);
		numberOfAccounts++;
	}
	
	public Savings(int acctNo, double balance) {
		super(acctNo, balance);
	}
	
	public Savings(double balance) {
		super(numberOfAccounts, balance);
		numberOfAccounts++;
	}
	
	public Savings deepCopy() {
		return new Savings(acctNo, balance);
	}
	
	public String getType() {
		return type;
	}

	@Override
	public boolean deposit(double amount) {
		// TODO Auto-generated method stub
		return super.deposit(amount);
	}

	@Override
	public boolean withdraw(double amount) {
		// TODO Auto-generated method stub
		System.out.println("Cannot withdraw from Savings Account.");
		return false;
	}
	
}
