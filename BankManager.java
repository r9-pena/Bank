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

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


public class BankManager extends Person{
	
	/**
	 * Constructor uses superclass Person constructor
	 */
	public BankManager() {
		super();
	}
	
	/**
	 * User can inquire about a specific customer's account
	 * @param custList HashMap containing all customers in the system
	 */
	public void inquire(HashMap<String, Customer> custList) {
		Scanner keyboard = new Scanner(System.in);
		String ans = "y";
		
		while(ans.equalsIgnoreCase("Y")) {
			System.out.println("Enter the name of the customer you would like to inquire about:");		
			String customer = keyboard.nextLine().toUpperCase();
			String account;
			
			if(!custList.containsKey(customer)) {
				System.out.println("Customer does not exist or entry is invalid. Would you like to retry?");
				System.out.println("Press \"y\" or \"Y\" to continue.");
				ans = keyboard.nextLine();
				continue;
			}
			
			if((account = acctSel()) == null) {
				System.out.println("Entry is invalid. Would you like to retry?");
				keyboard.nextLine();
				System.out.println("Press \"y\" or \"Y\" to continue.");
				ans = keyboard.nextLine();
				continue;
			}
			
			custList.get(customer).inquire(account);
			
			try {
				logInquiry(customer, account);
			}
			catch(IOException e) {
				System.out.println("Error: Transaction was not logged.");
			}
			
			System.out.println("Inquire about another account?");	
			keyboard.nextLine();
			System.out.println("Press \"y\" or \"Y\" to continue.");
			ans = keyboard.nextLine();
		}
	}
	
	/**
	 * Created by: Aaron Alarcon
	 * All inquiries made for the session are logged on a text file.
	 * @param customer Name of customer in the database
	 * @param account Type of account
	 * @throws IOException Throws exception
	 */
	public void logInquiry(String customer, String account) throws IOException {
		BufferedWriter b = new BufferedWriter(new FileWriter("Log.txt",true));
		String log = "Bank Manager: " + fullName + " - " + " Inquiry made on customer " + customer + 
					" on " + account + "account\n";
		b.append(log);
		b.close();
	}
 
	/**
	 * Created by: Ricardo Pena and Aaron Alarcon
	 * Method creates a new customer.
	 * User must add all information.
	 * Assumption: Information entered is correct. 
	 * @param custList HashMap containing all customers in the system
	 */
	public void newCustomer(HashMap<String, Customer> custList) {
		String ans;
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter new customer's first name: ");
		String fName = keyboard.nextLine();
		System.out.println("Enter new customer's last name: ");
		String lName = keyboard.nextLine();
		System.out.println("Enter new customer's date of birth: ");
		String dob = keyboard.nextLine();
		System.out.println("Enter new customer's address: ");
		String address = keyboard.nextLine();
		System.out.println("Enter new customer's phone number: ");
		String phone = keyboard.nextLine();
		System.out.println("Enter new customer's password: ");
		String pass = keyboard.nextLine();
		System.out.println("Enter the customer's email: ");
		String email = keyboard.nextLine();
		System.out.println("Enter new customer's initial deposit for Savings Account: ");
		double balance = keyboard.nextDouble();
		Savings save =  new Savings(balance);
		
		Checking check;
		System.out.println("Open a Checking Account?: ");
		ans = yesNo();
		if(ans.equalsIgnoreCase("Y")) {
			System.out.println("Enter new customer's initial deposit for Checking Account: ");
			balance = keyboard.nextDouble();
			check = new Checking(balance);
		}
		else
			check = null;
		
		Credit credit;
		System.out.println("Open a Credit Account?: ");
		ans = yesNo();
		if(ans.equalsIgnoreCase("Y")) {
			System.out.println("Enter new customer's initial deposit for Checking Account: ");
			balance = keyboard.nextDouble();
			credit = new Credit(balance);
		}
		else
			credit = null;
		
		Customer cust = new Customer(fName,lName,dob,address,phone, check,save,credit, pass, email);
		
		String key = cust.getFullName().toUpperCase();
		custList.put(key, cust);
	}
	
	/**
	 * Utility method prompts user if session should continue.
	 * @return String with a single character. Must be 'Y' or 'N' (not case sensitive).
	 */
	private static String yesNo() {
		Scanner keyboard = new Scanner(System.in);
		String ans;
		while(true) {
			System.out.println("Enter \"Y\" or \"N\"");
			ans = keyboard.nextLine();
			if(ans.equalsIgnoreCase("Y"))
				break;
			if(ans.equalsIgnoreCase("N"))
				break;
		}
		return ans;
		
	}
	
	/**
	 * Creates a bank statement for a specified customer.
	 * @param custList HashMap containing a list of all customers in the system
	 */
	public void createStatement(HashMap<String, Customer> custList) {
		Scanner keyboard = new Scanner(System.in);
		String ans = "y";
		while(ans.equalsIgnoreCase("Y")) {
			System.out.println("Enter the name of the customer you would like to print statement:");		
			String customer = keyboard.nextLine().toUpperCase();
			
			if(!custList.containsKey(customer)) {
				System.out.println("Customer does not exist or entry is invalid. Would you like to retry?");
				System.out.println("Press \"y\" or \"Y\" to continue.");
				ans = keyboard.nextLine();
				continue;
			}
			
			try {
				custList.get(customer).getStatement().writeBankStatement();	
			}
			catch(NullPointerException e) {
				System.out.println("Entry is not valid. Ending transaction.");	
				break;
			}
			
			System.out.println("Would you like to print another statement?");	
			keyboard.nextLine();
			System.out.println("Press \"y\" or \"Y\" to continue.");
			ans = keyboard.nextLine();
		}
		
	}
	
	/**
	 * Utility method to select account type
	 * @return String name account selected
	 */
	public static String acctSel() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please select account: ");
		System.out.println("For Checking Account enter: 1");
		System.out.println("For Savings Account enter: 2");
		System.out.println("For Credit Account enter: 3");
		
		String n = keyboard.nextLine();
		
		switch (n) {
		case "1":
			return "checking";
		case "2":
			return "savings";
		case "3":
			return "credit";
		}
		
		return null;
	}
}
