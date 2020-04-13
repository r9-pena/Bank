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

import java.io.*;
import java.util.Scanner;

public class Customer extends Person implements Log {
	private Checking checking;
	private Savings savings;
	private Credit credit;
	private static String fileName = "Log.txt";
	private String password;
	private BankStatement statement;
	
	/**
	 * 
	 * @param first First Name
	 * @param last Last Name
	 * @param dob Date of Birth
	 * @param idNo Social Security Number
	 * @param address Personal Address
	 * @param phone Primary Phone Number
	 * @param checking Checking Account
	 * @param savings Savings Account
	 * @param credit Credit Account
	 * @param email Email
	 * @param password Password
	 */
	public Customer(String first, String last, String dob, String idNo, String address, String phone, Checking checking, Savings savings, Credit credit, String password,String email) {
		
		super(first,last,dob,idNo,address,phone,email);
		this.password = password;

		if(checking == null) {
			this.checking = null;
		}
		else
			this.checking = checking.deepCopy();
		if(savings == null) {
			this.savings = null;
		}
		else
			this.savings = savings.deepCopy();
		if(credit == null) {
			this.credit = null;
		}
		else
			this.credit = credit.deepCopy();
		statement = new BankStatement(this);
	}
	
	public Customer(String first, String last, String dob, int idNo, String address, String phone, Checking checking, Savings savings, Credit credit, String password, String email) {

		super(first,last,dob,idNo,address,phone,email);
		this.password = password;

		if(checking == null) {
			this.checking = null;
		}
		else
			this.checking = checking.deepCopy();
		if(savings == null) {
			this.savings = null;
		}
		else
			this.savings = savings.deepCopy();
		if(credit == null) {
			this.credit = null;
		}
		else
			this.credit = credit.deepCopy();
		statement = new BankStatement(this);
		
	}
	
	public Customer(String first, String last, String dob, String address, String phone, Checking checking, Savings savings, Credit credit, String password, String email) {

		super(first,last,dob,address,phone,email);
		this.password = password;

		if(checking == null) {
			this.checking = null;
		}
		else
			this.checking = checking.deepCopy();
		if(savings == null) {
			this.savings = null;
		}
		else
			this.savings = savings.deepCopy();
		if(credit == null) {
			this.credit = null;
		}
		else
			this.credit = credit.deepCopy();
		statement = new BankStatement(this);
	}
	
	//Other methods
	/**
	 * This method serves to create a copy of an object to protect information.
	 * @return Returns a deep copy of the object.
	 */
	public Customer deepCopy() {
		return new Customer(firstName,lastName,dob,idNo,address,phoneNo,checking,savings,credit,password,email);
	}
	
	/**
	 * Created by: Aaron Alarcon
	 * Creates a String with the customer information
	 */
	public String toString() {
		
		int checkAcctNo = 0;
		int savAcctNo = 0;
		int creditAcctNo = 0;
		double checkingBal = 0;
		double savingBal = 0;
		double creditBal = 0;
		
		if(checking != null) {
			checkAcctNo = checking.getAcctNo();
			checkingBal = checking.getBalance();
		}
		
		if(savings != null) {
			savAcctNo = savings.getAcctNo();
			savingBal = savings.getBalance();
		}
		
		if(credit != null) {
			creditAcctNo = credit.getAcctNo();
			creditBal = credit.getBalance();
		}
		
		return firstName + "," + lastName + "," + dob + "," + idNo + ",\"" + address +"\"," + phoneNo + "," + checkAcctNo + "," + savAcctNo + "," + creditAcctNo + "," + checkingBal + "," + savingBal + "," + creditBal;
	}
	
	//Transaction methods
	/**
	 * This method provides the current balance of an account.
	 * @param account Account type which is being inquired about.
	 */
	public void inquire(String account) {
		Account acct = acctSel(account);
		if(acct == null) {
			System.out.println("This is not a valid entry. Transaction terminated.");
		}
		else {
			System.out.println("Current account balance is " + acct.getBalance());
			LogInquiry(acct);
			statement.LogInquiry(acct);
		}	
	}
	/**
	 * This method deposits a specified amount into the desired account.
	 * @param account Account type to be deposited into
	 * @param amount Amount being deposited.
	 * @return Returns True if deposit was completed. Returns False if deposit fails.
	 */
	public boolean deposit(String account, double amount) {
		Account acct = acctSel(account);
		if(account.equals(null) || acct == null)
			return false;
		else if(!acct.deposit(amount))
			return false;
		else {
			System.out.println("Amount deposited: " + amount + " , Remaining Balance:" + acct.getBalance());
			LogDeposit(amount, acct, "");
			statement.LogDeposit(amount, acct, "");
			return true;
		}
	}
	/**
	 * This method withdraws a specified amount from the desired account.
	 * @param account Account type to be withdrawn from
	 * @param amount Amount being withdrawn.
	 * @return Returns True if withdrawal was completed. Returns False if withdrawal failed.
	 */
	public boolean withdraw(String account, double amount) {
		Account acct = acctSel(account);
		if(account.equals(null) || acct == null)
			return false;
		else if(!acct.withdraw(amount)) {
			return false;
		}
		else {
			System.out.println("Amount Withdrawn: " + amount + ", Remaining Balance: " + acct.getBalance());
			LogWithdrawal(acct,amount);
			statement.LogWithdrawal(acct, amount);
			return true;
		}
	}

  //New pay/transfer function so that my log works with it, and so that //everything isn't printed out twice. Also gets rid of extra methods in main. Untested**** but should work
  //check this out
	public boolean Pay(String account1, String account2, Customer receiver, double amount){
		Account acc1 = acctSel(account1);
		Account acc2 = acctSel(account2);
		if(!acc1.withdraw(amount)){
			System.out.println("Insufficient funds for the transaction");
			return false;
		}
		if(!acc2.deposit(amount)){
			acc1.deposit(amount);
			return false;
		}
		String name = getFullName();
		if(receiver != null){
			String recName = receiver.getFullName();
			System.out.println(name + " has paid " + recName + " " + amount + " from his " + acc1.getType() + " account to " + recName + "'s " + acc2.getType() + " account. Remaining balance for " + name + " - " + acc1.getBalance() + ". Remaining balance for " + recName + " - " + acc2.getBalance() + ".");
			LogPayment(acc1, acc2, receiver, amount);
			statement.LogPayment(acc1, acc2, receiver, amount); 
		}
		else{
			System.out.println(amount + " has been transferred from your "+ acc1.getType() +" account to your account. Remaining balance for " + acc1.getType() + " - " + acc1.getBalance() + ". Remaining balance for " + acc2.getType() + " - " + acc1.getBalance());
			LogTransfer(amount, acc1, acc2);
			statement.LogTransfer(amount, acc1, acc2);
		}

		return true;
	}
	
	/**
	 * Utility method to select account
	 * @param account Account type
	 * @return Account object
	 */
	private Account acctSel(String account) {
		if(account == null)
			return null;
		
		switch (account.toUpperCase()) {
		case "CHECKING":
			return checking;
		case "SAVINGS":
			return savings;
		case "CREDIT":
			return credit;
		}
		
		return null;
	}
	/**
	 * @author Aaron Alarcon
     * Writes into the log file that a balance inquiry was made
     * @param acc - the account the inquiry was made on
     */
    public void LogInquiry(Account acc){
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(fileName,true));
            String name = getFullName();
            b.append(String.format("%s made a balance inquiry on %s - %d. %s's balance for %s - %d: $%.2f.\n", name, acc.getType(), acc.getAcctNo(), name, acc.getType(), acc.getAcctNo(), acc.getBalance()));
            b.close();
        }
        catch (IOException I){
            I.printStackTrace();
        }
    }

    /**
     * @author Aaron Alarcon
     * Writes into the Log file that a withdrawal was made
     * @param acc - the account the withdrawal was done on
     * @param amount - the amount that was withdrawn from the account
     */
    public void LogWithdrawal(Account acc, double amount){
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(fileName,true));
            String name = getFullName();
            b.append(String.format("%s withdrew $%.2f from  %s - %d. %s's new balance for %s - %d:  $%.2f.\n", name, amount, acc.getType(), acc.getAcctNo(), name,acc.getType(), acc.getAcctNo(), acc.getBalance()));
            b.close();
        }
        catch (IOException I){
            I.printStackTrace();
        }
    }

    /**
     * @author Aaron Alarcon
     * Writes into the Log file that a deposit was made
     * @param amount - the amount deposited/paid
     * @param acc - the account the action was performed on
     * @param from - not used here, but used in the bankStatement version
     */
    public void LogDeposit(double amount, Account acc, String from){
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(fileName,true));
            String name = getFullName();
            b.append(String.format("%s made a deposit of $%.2f in %s-%d. %s's balance for %s-%d: $%.2f.\n", name, amount, acc.getType(), acc.getAcctNo(), name,acc.getType(), acc.getAcctNo(), acc.getBalance()));
            b.close();
        }
        catch (IOException I){
            I.printStackTrace();
        }
    }

    /**
     * @author Aaron Alarcon
     * Writes into the Log file that a payment was made to another customer
     * @param acc1 - the account the money is from
     * @param acc2 - the account the money is paid to
     * @param receiver - the customer receiving the payment
     * @param amount - the amount that was paid
     */
    public void LogPayment(Account acc1, Account acc2,  Customer receiver, double amount){
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(fileName,true));
            String name = getFullName();
            String recName = receiver.getFullName();
            b.append(String.format("%s paid %s $%.2f from %s - %d. %s's balance for %s - %d: $%.2f.\n", name, recName,  amount, acc1.getType(), acc1.getAcctNo(), name, acc1.getType(), acc1.getAcctNo(), acc1.getBalance()));
            b.append(String.format("%s received $%.2f from %s.%s's balance for %s - %d: $%.2f.\n", recName, amount, name, recName, acc2.getType(), acc2.getAcctNo(), acc2.getBalance()));
            b.close();
        }
        catch (IOException I){
            I.printStackTrace();
        }
    }

    /**
     * @author Aaron Alarcon
     * Writes into the Log file that a transfer was made between a users accounts
     * @param amount - the amount transferred
     * @param acc1 - the account that the money was transferred from
     * @param acc2 - the account that the money was transferred to
     */
    public void LogTransfer(double amount, Account acc1, Account acc2){
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(fileName,true));
            String name = getFullName();
            b.append(String.format("%s transferred  $%.2f from %s - %d to %s - %d. %s's balance for %s - %d: $%.2f. %s's balance for %s -%d:$%.2f\n", name, amount, acc1.getType(), acc1.getAcctNo(), acc2.getType(), acc2.getAcctNo(), name, acc1.getType(), acc1.getAcctNo(), acc1.getBalance(), name, acc2.getType(), acc2.getAcctNo(), acc2.getBalance()));
            b.close();
        }
        catch (IOException I){
            I.printStackTrace();
        }
    }

    /**
     * Getter
     * @return BankStatement
     */
    public BankStatement getStatement(){
    	return statement;
    }
    /**
     * Getter
     * @return String
     */
    public String getPassword(){
    	return password;
    }
    /**
     * Getter
     * @return Checking
     */
    public Checking getChecking(){
    	return checking;
    }
    /**
     * Getter
     * @return Savings
     */
    public Savings getSavings(){
    	return savings;
    }
    /**
     * Getter
     * @return Credit
     */
    public Credit getCredit(){
    	return credit;
    }

}
