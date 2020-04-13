/**
 * @author Ricardo Pena and Aaron Alarcon
 * PinkBlink
 * November 26, 2019
 * CS3331
 * Daniel Mejia
 * Programming Assignment 5
 * 
 * The template for this class is from Aaron Alarcon, except as noted for each method
 * 
 * Create a customer which has checking/savings/credit accounts and simulate various transactions.
 * I confirm that the work of this assignment is completely my own.By turning in this assignment,
 * I declare that I did not receive unauthorized assistance. Moreover, all the deliverables 
 * including, but not limited to the source code, lab report and output files were written and 
 * produced by me alone.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class BankStatement implements Log{

    String[] transactions;
    Customer C;

    /**
     * Makes a bank statement object using a customer that it is linked to
     * @param C- the customer that this statement is linked to
     */
    public BankStatement(Customer C){
        this.C = C;
        transactions = new String[3];
        initializeTrans();
    }


    /**
     * Adds Initial Balance to the transaction history
     */
    public void initializeTrans(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        transactions[0] =String.format("%s\tOriginal Balance\t\t0\t\t\t0\t\t\t $%.2f.\n", dtf.format(localDate),  C.getChecking().getBalance());
        transactions[1] = String.format("%s\tOriginal Balance\t\t0\t\t\t0\t\t\t$%.2f.\n", dtf.format(localDate),  C.getSavings().getBalance());
        transactions[2] = String.format("%s\tOriginal Balance\t\t0\t\t\t0\t\t\t$%.2f.\n", dtf.format(localDate),  C.getCredit().getBalance());

    }

    /**
     * Concatenates the Action of inquiring balance into the transaction history
     * @param acc - the account the action is done on
     */
    public void LogInquiry(Account acc){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String type = acc.getType();
        String name = C.getFirstName() + " " + C.getLastName();
        int index = 0;
        if(type.equals("Savings")){
          index = 1;
        }
        else if(type.equals("Credit")){
          index = 2;
        }
        transactions[index] = transactions[index].concat(String.format("%s\tBalance Inquiry\t\t\t0\t\t0\t\t $%.2f.\n", dtf.format(localDate),  acc.getBalance()));
    }

    /**
     * Concatenates the withdrawal into the transaction history of the respective account
     * @param amount - the amount that was withdrawn from the account
     * @param acc - the account money is withdrawn from
     */
    public void LogWithdrawal(Account acc, double amount){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String type = acc.getType();
        int index = 0;
        if(type.equals("Savings")){
          index = 1;
        }
        else if(type.equals("Credit")){
          index = 2;
        }
        transactions[index] = transactions[index].concat(String.format("%s\tWithdrawal\t\t\t$%.2f\t\t0\t\t$%.2f.\n",dtf.format(localDate),  amount, acc.getBalance()));
    }

    /**
     * Concatenates the deposit into the transaction history of the respective account
     * @param amount - the amount deposited/paid
     * @param acc - the account the action was performed on
     * @param from - Customer name who deposits money
     */
    public void LogDeposit(double amount, Account acc, String from){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String type = acc.getType();
        int index = 0;
        if(type.equals("Savings")){
          index = 1;
        }
        else if(type.equals("Credit")){
          index = 2;
        }
        if(from.equals("")) {
          transactions[index] = transactions[index].concat(String.format("%s\tDeposit\t\t\t0\t\t\t$%.2f\t\t$%.2f\n", dtf.format(localDate), amount, acc.getBalance()));
        }
        else{
          transactions[index] = transactions[index].concat(String.format("%s\tPayment from %s\t\t0\t\t$%.2f\t\t$%.2f\n", dtf.format(localDate), from, amount, acc.getBalance()));
        }
    }

    /**
     * Concatenates the payment to the transactions of both users
     * @param acc1 - Account making payment
     * @param acc2 - Account receiving payment
     * @param receiver - the customer receiving the payment
     * @param amount - the amount that was paid
     */
    public void LogPayment(Account acc1, Account acc2, Customer receiver, double amount){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        String name = C.getFirstName() + " " + C.getLastName();
        String recName = receiver.getFirstName() + " " + receiver.getLastName();
        String type = acc1.getType();
        int index = 0;
        if(type.equals("Savings")){
          index = 1;
        }
        else if(type.equals("Credit")){
          index = 2;
        }
        transactions[index] = transactions[index].concat(String.format("%s\tPaid %s money\t\t$%.2f\t\t0\t\t$%.2f\n",dtf.format(localDate),  recName,  amount, acc1.getBalance()));
        receiver.getStatement().LogDeposit(amount, acc2, name);

    }

    /**
     * Concatenate the most recent transaction to the accounts the transfer applies to
     * @param amount - the amount transferred
     * @param acc1 - the account that the money was transferred from
     * @param acc2 - the account that the money was transferred to
     */
    public void LogTransfer(double amount, Account acc1, Account acc2){
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd");
      LocalDate localDate = LocalDate.now();
      String type1 = acc1.getType();
      int index1 = 0;
      if(type1.equals("Savings")){
        index1 = 1;
      }
      else if(type1.equals("Credit")){
        index1 = 2;
      }

      String type2 = acc2.getType();
      int index2 = 0;
      if(type2.equals("Savings")){
        index2 = 1;
      }
      else if(type2.equals("Credit")){
        index2 = 2;
      }
      transactions[index1] = transactions[index1].concat(String.format("%s\tTransfer to %s\t\t$%.2f\t\t0\t\t$%.2f\n", dtf.format(localDate), type2, amount, acc1.getBalance()));

      transactions[index2] = transactions[index2].concat(String.format("%s\tTransfer from %s\t\t0\t\t$%.2f\t\t$%.2f\n", dtf.format(localDate), type1, amount, acc2.getBalance()));
            
    }

    /**
     * Uses all the information in the class to write a bank statement file for the user
     */
    public void writeBankStatement(){
        try {
            //print all personal information
            String header = "Date\t\t\tMemo\t\t\tWithdrawals\t\tDeposits\t\tBalance\n";
            BufferedWriter b = new BufferedWriter(new FileWriter(C.getFullName() + "Bankstatement.txt",false));
            b.append("Bank of Captain America\n");
            b.append("Customer: " + C.getFullName() + "\n");
            b.append("Date of Birth: " +  C.getDob() + "\n");
            b.append("Identification Number: " + C.getIdNo() + "\n");
            b.append("Address: " + C.getAddress() + "\n");
            b.append("Phone Number: " + C.getPhoneNo() + "\n");

            //print account information with all transactions
            b.append(String.format("Information for Checking - %d\n",C.getChecking().getAcctNo()));
            b.append(header);
            b.append(transactions[0]);
            b.append("\n\n");
            b.append(String.format("Information for Savings - %d\n", C.getSavings().getAcctNo()));
            b.append(header);
            b.append(transactions[1]);
            b.append("\n\n");
            b.append(String.format("Information for Credit - %d\n", C.getCredit().getAcctNo()));
            b.append(header);
            b.append(transactions[2]);
            b.close();
        }
        catch (IOException I){
            I.printStackTrace();
        }
    }


}
