/**
 * @author Ricardo Pena and Aaron Alarcon
 * PinkBlink
 * November 26, 2019
 * CS3331
 * Daniel Mejia
 * Programming Assignment 5
 * 
 * The template for this class is from Ricardo Pena.
 * Individual modifications are noted above each method.
 * 
 * Create a customer which has checking/savings/credit accounts and simulate various transactions.
 * 
 * I confirm that the work of this assignment is completely my own.By turning in this assignment,
 * I declare that I did not receive unauthorized assistance. Moreover, all the deliverables 
 * including, but not limited to the source code, lab report and output files were written and 
 * produced by me alone.
 */

import java.util.*;
import java.io.*;


public class RunBank {
	
	static Scanner keyboard = new Scanner(System.in);
	static HashMap<String, Customer> hmap = new HashMap<String, Customer>();
	static String row;
	static String ans = "y";
	static BankManager manager = new BankManager();
	static String importFile = "Bank_Users_5.txt";
	static String fileName = "Log.csv";

	public static void main(String[] args) {
	
		readCustomerData();
		createLog();
		access();
		finalBalance();
		keyboard.close();

	}
	
	/**
	 * This function allows the user to select the function they will to undertake.
	 * The user has the ability to move from one function to another until the exit command has been entered.
	 * 
	 */
	public static void access() {
		String selection;
		
		while(true) {
			System.out.println("Enter type of access.");
			System.out.println("1. Bank Manager" +"\n2. Customer" + "\n3. Transaction Reader" + "\n4. Exit");
			selection = keyboard.nextLine();
			
			if(selection.equals("1")) {
				managerRole();
				continue;
			}
			else if(selection.equals("2")) {
				customerRole();
				continue;
			}
			else if(selection.equals("3")) {
				transactionReader();
				continue;
			}
			else if(selection.equals("4")) {
				break;
			}
			else {
				System.out.println("Entry was INVALID. Please try again.");
			}
		}		
	}
	
   /**
     * Created by: Aaron Alarcon
     * Reads text file and inserts into a HashMap of customers based off the info.
     * The key for each user is the full name of the customer.
     */
    public static void readCustomerData() {
        String line;
        String key = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(importFile));

            //Array of names we're looking for in our info line
            String[] names = {"First Name", "Last Name",  "Date of Birth", "Identification Number","Address", "Phone Number", "Checking Account Number", "Savings Account Number","Credit Account Number","Checking Starting Balance","Savings Starting Balance","Credit Starting Balance", "Credit Max", "Password", "email"};
            String[] str = br.readLine().split("\t");
            int largest = str.length;
            int[] result = {largest, largest, largest, largest, largest, largest, largest, largest, largest, largest, largest, largest, largest, largest, largest};

            //Searching for our names and if it's found, setting the correct index in the result array
            for(int i = 0; i < names.length; i++){
                for(int j = 0; j<str.length; j++){
                    if(names[i].equalsIgnoreCase(str[j])){
                        result[i] = j;
                        break;
                    }
                }
            }

           
            while ((line = br.readLine()) != null) {
                line = line.concat("\t ");
                String[] D = line.split("\t");
                D[largest] = "";
                D[result[3]] = D[result[3]].replace("-","");

                //make a new customer from the data
                Customer C = new Customer(D[result[0]], D[result[1]], D[result[2]], getInt(D[result[3]]), D[result[4]], D[result[5]], new Checking(getInt(D[result[6]]), getDoub(D[result[9]])), new Savings(getInt(D[result[7]]), getDoub(D[result[10]])), new Credit(getInt(D[result[8]]), getDoub(D[result[11]]), getDoub(D[result[12]])), D[result[13]], D[result[14]]);

                //Use the full name as a key
                key = C.getFullName().toUpperCase();
                hmap.put(key, C.deepCopy());

                //clearing up variables
                key = null;
                C = null;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("There was an error opening the file. Please make sure everything is named correctly and your file exists");
            System.exit(-1);
        }
    }

    /**
     * Created by: Aaron Alarcon
     * Utility method to get an integer from a string or return 0 if it's null
     * @param s - the string to get an integer from
     * @return - an integer representation of the string
     */
    public static int getInt(String s){
        if(s.equals("")){
            return 0;
        }
        return Integer.valueOf(s);
    }

    /**
     * Created by: Aaron Alarcon
     * Utility method to get a double from a string or return 0 if it's null
     * @param s - the string to get a double from
     * @return - a double representation of the string
     */
    public static double getDoub(String s){
        if(s.equals("")){
            return 0.0;
        }
        return Double.valueOf(s);
    }
	
    /**
     * Method provides functionality for manager role
     * Manager may inquire about customers, add new customer, or print a customer bank statement.
     */
	public static void managerRole() {
		String selection;
		
		while(true) {
			System.out.println("What would you like to do?");
			System.out.println("1. Inquire" +"\n2. Add New Customer" + "\n3. Create Bank Statement" + "\n4. Exit");
			selection = keyboard.nextLine();
			
			if(selection.equals("1")) {
				manager.inquire(hmap);
				continue;
			}
			else if(selection.equals("2")) {
				manager.newCustomer(hmap);
				continue;
			}
			else if(selection.equals("3")) {
				manager.createStatement(hmap);
				continue;
			}
			else if(selection.equals("4")) {
				break;
			}
			else {
				System.out.println("Entry was INVALID. Please try again.");
			}
		}	
	}
	
	/**
	 * Method provides functionality for Customer role.
	 * Customer may inquire, deposit, withdraw, transfer, pay, or print bank statement.
	 */
 	public static void customerRole() {
		while(ans.equalsIgnoreCase("y")) {
			System.out.println("Enter your full name...");
			
			String customer = keyboard.nextLine().toUpperCase();
			
			if(customer.equals("0")) {
				break;
			}
			
			if(!hmap.containsKey(customer)) {
				System.out.println("Customer does not exist or entry is invalid. Please re-enter name or \"0\" to EXIT.");
				continue;
			}

			System.out.println("Enter password...");
			String password = keyboard.nextLine();
			if(!hmap.get(customer).getPassword().equals(password)) {
				System.out.println("Password is invalid. Please try again or \"0\" to EXIT.");
				continue;
			}
			
			transaction(customer);
			
			System.out.println("Continue with another transaction?");			
			keyboard.nextLine();
			System.out.println("Press \"y\" or \"Y\" to continue.");
			ans = keyboard.nextLine();
		}
		
	}
	
 	/**
 	 * Method reads specific commands from a file.
 	 * Transactions are organized to be executed based on key words.
 	 * Key words: Deposits, Withdraws, Transfers, Pays
 	 */
	public static void transactionReader() {
		try {
			String name1, name2, account1, account2, type, amount;
			name1 = "";
			account1 ="";
			//Open file to read-only mode
			FileReader read = new FileReader(new File("Transaction Actions.csv"));
			//Use buffered reader to read
			BufferedReader readCSV = new BufferedReader(read);
			//Skip the file headers
			row = readCSV.readLine();
			int size = row.split(",").length;
			String[] col = new String[size];
			//row = readCSV.readLine();
			
			//Read each line from the file and pass it to the customer constructor
			while ((row = readCSV.readLine()) != null) {
				//Separate each column
				String[] temp = row.split(",|%");
				for(int i = 0; i < temp.length; i++) {
					col[i] = temp[i];
				}
				//System.out.println(col[0]+col[1]+col[2]+col[4]+col[5]);
				//Transaction Type and Amount
				type = col[3];
				amount = col[7];
				//Customer 1 Information
				if(!col[0].isBlank()) {
					name1 = (col[0] + " " + col[1]).toUpperCase();
					account1 = col[2];
				}
				//Customer 2 Information
				name2 = (col[4] + " " + col[5]).toUpperCase();
				account2 = col[6];
				System.out.println(name1+account1+type+name2+account2+amount);
				
				transaction(type,name1,account1,name2,account2,amount);
			}
			//Close file
			readCSV.close();
		}
		catch (IOException e) {
			System.out.println("File not found.");
		}
	}
	
	/**
	 * Based on the transaction type selected by the user, the operation is executed.
	 * @param customer Full name of customer
	 */
	public static void transaction(String customer) {
		double amount = 0;
		String acct2;
		
		String type = transactionSelect();
		
		System.out.println("Select account:");
		String account = acctSel();

		switch (type.toUpperCase()) {
		
		case "INQUIRE":
			hmap.get(customer).inquire(account);
			break;
			
		case "DEPOSIT":
			System.out.println("Enter amount to " + type);
			amount = Double.parseDouble(keyboard.nextLine());
			if(amount <= 0)
				break;
			hmap.get(customer).deposit(account,amount);
			break;
			
		case "WITHDRAW":
			System.out.println("Enter amount to " + type);
			amount = Double.parseDouble(keyboard.nextLine());
			if(amount <= 0)
				break;
			hmap.get(customer).withdraw(account,amount);	
			break;
			
		case "TRANSFER":
			System.out.println("Select account to transfer to:");
			acct2 = acctSel();
			System.out.println("Enter amount to " + type);
			amount = Double.parseDouble(keyboard.nextLine());
			if(amount <= 0)
				break;
			hmap.get(customer).Pay(account, acct2, null, amount);
			break;
			
		case "PAY":			
			System.out.println("Enter recipients name...");
			String cust2 = keyboard.nextLine().toUpperCase();
			System.out.println("Enter amount to " + type);
			amount = Double.parseDouble(keyboard.nextLine());
			if(!hmap.containsKey(cust2)) {
				System.out.println("Customer does not exist or entry is invalid. Transaction terminated.");
				break;
			}
			System.out.println("Select account:");
			acct2 = acctSel();
			if(amount <= 0) {
				break;
			}
			hmap.get(customer).Pay(account, acct2, hmap.get(cust2), amount);
			break;
		
		case "STATEMENT":
			System.out.println("Writing your statement in a new file");
			try {
				hmap.get(customer).getStatement().writeBankStatement();	
			}
			catch(NullPointerException e) {
				System.out.println("Entry is not valid. Ending transaction.");	
				break;
			}
			break;
		default:
			System.out.println("Entry is not valid.");
		}
		
	}
	
	/**
	 * Transactions are performed automatically for all respective users and accounts.
	 * @param type Transaction Type: Deposit, Withdraw, Transfer, Pay
	 * @param cust1 Customer initiating transaction 
	 * @param acct1 Account type of customer initiating transaction
	 * @param cust2 Customer on receiving end of transaction (optional)
	 * @param acct2 Account of receiving Customer (optional)
	 * @param amnt Amount of the transaction
	 */
	public static void transaction(String type,String cust1,String acct1,String cust2,String acct2,String amnt) {
		
		double amount = Double.parseDouble(amnt);
		switch (type.toUpperCase()) {
		case "INQUIRES":
			hmap.get(cust1).inquire(acct1);
			break;
		case "DEPOSITS":
			if(amount <= 0)
				break;
			hmap.get(cust1).deposit(acct1,amount);
			break;
		case "WITHDRAWS":
			if(amount <= 0)
				break;
			hmap.get(cust1).withdraw(acct1,amount);	
			break;
		case "TRANSFERS":
			if(!hmap.containsKey(cust2))
				break;
			if(amount <= 0)
				break;
			hmap.get(cust1).Pay(acct1, acct2, null, amount);			
			break;
		case "PAYS":
			if(!hmap.containsKey(cust2)) {
				break;
			}	
			if(amount <= 0) {
				break;
			}
			hmap.get(cust1).Pay(acct1, acct2, hmap.get(cust2), amount);
			break;
		default:
			System.out.println("Entry is not valid.");
		}
	}
	
  /**
  * Clears out the previous log file
  */
	public static void createLog() {
		try {
			BufferedWriter b = new BufferedWriter(new FileWriter("Log.txt"));
			b.write("");
			b.close();
		}catch (IOException I) {
			I.printStackTrace();
		}
	}	
	
	/**
	 * Prints out Final Balance for all customers.
	 * Output is generated in alphabetical order A-Z.
	 * Output file is CSV file.
	 */
	public static void finalBalance() {
		//Export all keys into an array to be sorted
		Set<String> key = hmap.keySet();
		String[] arr = key.toArray(new String[key.size()]);
		//Sort keys by first name
		Arrays.sort(arr);
		String log;
		String header = ("First Name,Last Name,Date of Birth,Identification,Address,Phone Number," +
						"Checking Account,Savings Account,Credit Account,Checking Balance,Savings Balance,Credit Balance");
		try {
			FileWriter writeCSV = new FileWriter("BalanceSheet.csv", false);
			writeCSV.append(header);
			writeCSV.append(System.lineSeparator());
			for(int i = 0; i < arr.length; i++) {
				log = "\n" + hmap.get(arr[i]).toString();
				//writeCSV.append(System.lineSeparator());
				writeCSV.append(log);
			}
			writeCSV.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
				
	}

	/**
	 * This method request the user to select the account to be used for the transaction.
	 * @return Returns the account or null if the selection is invalid.
	 */
	public static String acctSel() {
		keyboard = new Scanner(System.in);
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
	
	/**
	 * This method requests the user to select the type of transaction to be executed.
	 * @return Returns String value of transaction or null value if selection is in valid.
	 */
	public static String transactionSelect() {
		keyboard = new Scanner(System.in);
		System.out.println("How can we help you today?");
		System.out.println("If you would like to make an inquiry, enter \"1\".");
		System.out.println("If you would like to make a deposit, enter \"2\".");
		System.out.println("If you would like to make a withdrawal, enter \"3\".");
		System.out.println("If you would like to make a transfer, enter \"4\".");
		System.out.println("If you would like to make a payment, enter \"5\".");
		System.out.println("If you would like to print statement, enter \"6\".");
		
		String n = keyboard.nextLine();
		
		switch (n) {
		case "1":
			return "INQUIRE";
		case "2":
			return "DEPOSIT";
		case "3":
			return "WITHDRAW";
		case "4":
			return "TRANSFER";
		case "5":
			return "PAY";
		case "6":
			return "STATEMENT";
		}
		return " ";
	}
}

