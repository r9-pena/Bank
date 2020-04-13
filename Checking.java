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
 * I confirm that the work of this assignment is completely my own.By turning in this assignment,
 * I declare that I did not receive unauthorized assistance. Moreover, all the deliverables 
 * including, but not limited to the source code, lab report and output files were written and 
 * produced by me alone.
 */

public class Checking extends Account {
	
	private static int numberOfAccounts = 1000;
	
	final private static String type = "Checking"; 
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 */
	public Checking(String acctNo, String balance) {
		super(acctNo, balance);
		numberOfAccounts++;
	}
	
	/**
	 * Constructor
	 * @param acctNo Account Number
	 * @param balance Balance of account
	 */
	public Checking(int acctNo, double balance) {
		super(acctNo, balance);
	}
	
	/**
	 * Constructor
	 * @param balance Balance of account
	 */
	public Checking(double balance) {
		super(numberOfAccounts, balance);
		numberOfAccounts++;
		
	}
	
	/**
	 * Creates a deep copy of the class object
	 * @return Returns deep copy of class object
	 */
	public Checking deepCopy() {
		return new Checking(acctNo, balance);
	}
	
	/**
	 * Getter
	 * @return String of account type
	 */
	public String getType() {
		return type;
	}

	@Override
	public boolean deposit(double amount) {
		// TODO Auto-generated method stub
		return super.deposit(amount);
	}

	@Override
	/**
	 * Performs super operation
	 * @return Boolean value if operation is executed
	 */
	public boolean withdraw(double amount) {
		// TODO Auto-generated method stub
		if(amount > balance) {
			System.out.println("Insufficient funds to complete transaction.");
			return false;
		}
		else
			return super.withdraw(amount);
	}
	
	

}
