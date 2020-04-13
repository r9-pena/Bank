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
 * Log Interface
 * 
 * I confirm that the work of this assignment is completely my own.By turning in this assignment,
 * I declare that I did not receive unauthorized assistance. Moreover, all the deliverables 
 * including, but not limited to the source code, lab report and output files were written and 
 * produced by me alone.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public interface Log {
    public void LogInquiry(Account acc);
    public void LogWithdrawal(Account acc, double amount);
    public void LogDeposit(double amount, Account acc, String from);
    public void LogPayment(Account acc1, Account acc2,  Customer receiver, double amount);
    public void LogTransfer(double amount, Account acc1, Account acc2);
}
