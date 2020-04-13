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

public abstract class Person {
	String firstName, lastName;
	String fullName;
	String dob;
	int idNo;
	String address;
	String phoneNo;
	String email;
	
	private static int numOfCustomer = 1;
	
	public Person() {
		firstName = "Ricardo";
		lastName = "Pena";
		dob = "1234";
		idNo = 9999;
		address = "Earth";
		phoneNo = "1800";
		email = "rpena@gmail.com";
		
	}
	
	/**
	 * 
	 * @param first First Name
	 * @param last Last Name
	 * @param dob Date of Birth
	 * @param idNo Social Security Number
	 * @param address Personal Address
	 * @param phone Primary Phone Number
	 * @param email- the email of the person
	 */
	public Person(String first, String last, String dob, String idNo, String address, String phone, String email) {
		this.firstName = first;
		this.lastName = last;
		this.fullName = firstName + " " + lastName;
		this.dob = dob;
		this.idNo = Integer.parseInt(idNo.replace("-",""));
		this.address = address.replace("\"", "");
		this.phoneNo = phone;
		this.email = email;
		this.numOfCustomer++;
		
	}
	/**
	 * 
	 * @param first First Name
	 * @param last Last Name
	 * @param dob Date of Birth
	 * @param idNo Social Security Number
	 * @param address Personal Address
	 * @param phone Primary Phone Number
	 * @param email Email
	 */
	public Person(String first, String last, String dob, int idNo, String address, String phone, String email) {
		this.firstName = first;
		this.lastName = last;
		this.fullName = firstName + " " + lastName;
		this.dob = dob;
		this.idNo = idNo;
		this.address = address;
		this.phoneNo = phone;
    this.email = email;
		
	}
	
	public Person(String first, String last, String dob, String address, String phone, String email) {
		this.firstName = first;
		this.lastName = last;
		this.fullName = firstName + " " + lastName;
		this.dob = dob;
		this.idNo = this.numOfCustomer;
		this.address = address;
		this.phoneNo = phone;
    this.email = email;
		
		this.numOfCustomer++;
	}
	
	//Getters & Setters
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public int getIdNo() {
		return idNo;
	}
	public void setIdNo(int idNo) {
		this.idNo = idNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
  public void setEmail(String email){
    this.email = email;
  }
  public String getEmail(){
    return email;
  }

}
