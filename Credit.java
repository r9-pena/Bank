import java.io.IOException;

/**
 * @author Ricardo Pena and Aaron Alarcon
 * PinkBlink
 * November 26, 2019
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

public class Credit extends Account {
	private static int numberOfAccounts = 3000;
	final private static String type = "Credit";
	private double limit;
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 */
	public Credit(String acctNo, String balance) {
		super(acctNo, balance);
		limit = Integer.MAX_VALUE;
		numberOfAccounts++;
	}
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 * @param limit Limit of account
	 */
	public Credit(String acctNo, String balance, double limit) {
		super(acctNo, balance);
		this.limit = limit;
		numberOfAccounts++;
	}
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 */
	public Credit(int acctNo, double balance) {
		super(acctNo, balance);
	}
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 * @param limit Limit of account
	 */
	public Credit(int acctNo, double balance, double limit) {
		super(acctNo, balance);
		this.limit = limit;
	}
	
	/**
	 * Constructor
	 * @param balance Balance of account
	 */
	public Credit(double balance) {
		super(numberOfAccounts, balance);
		numberOfAccounts++;
	}
	
	/**
	 * Creates a deep copy of the class object
	 * @return Returns deep copy of class object
	 */
	public Credit deepCopy() {
		return new Credit(acctNo, balance, limit);
	}
	
	/**
	 * Setter
	 * @param limit Limit of account
	 */
	public void setLimit(double limit) {
		this.limit = limit;
	}
	
	/**
	 * Getter
	 * @return limit
	 */
	public double getLimit() {
		return limit;
	}
	
	/**
	 * Getter
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	@Override
	public boolean deposit(double amount) {
		// TODO Auto-generated method stub
		if(amount > Math.abs(balance)) {
			System.out.println("Remaining balance is " + balance + ". Cannot pay more than remaining balance.");
			return false;
		}
		else {
			return super.deposit(amount);
		}
	}

	@Override
	public boolean withdraw(double amount) {
		// TODO Auto-generated method stub
		if(Math.abs(balance - amount) > Math.abs(limit)) {
			System.out.println("Credit Limit of "+ this.limit +" has been reached.");
			return false;
		}
		else {
			return super.withdraw(amount);
		}
	}
	
}
