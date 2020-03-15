/**
 *                  Revision History (Newest First)
 * **************************************************************
 * 10/20/19 - Finished project - Thomas Bahun
 * 10/19/19 - testing and debugging - Thomas Bahun
 * 10/18/19 - Worked on methods - Thomas Bahun
 * 10/8/19 - Started work on project - Thomas Bahun
 */

package doghotel;

import java.util.*;
import java.io.*;

/**
 * Course: CSCI 160
 * Class Description: A class representing the dogs as guests in the hotel
 * Project Name: Dog Hotel
 * Due Date: 10/19/19
 * Depends on:
 * Extends: Pet
 * Implements:
 * @author thomas
 */
public class Guest extends Pet {
    
    // input properties
    
    /**
     * an int for the room number where the guest is in the hotel
     */
    private int roomNumber;
    /**
     * a Date for the guest's check in date
     */
    private Date checkInDate;
    
    // determined properties
    
    /**
     * a String for the type of food for the guest
     */
    private String typeOfFood;
    /**
     * a double representing the amount of food for the guest
     */
    private double amountOfFood;

    /**
     * parameterized constructor to create a dog before/outside 
     * of check in process. checkInGuest sets checkInDate and roomNumber 
     * if check in success before adding the guest to the ArrayList
     * @param dob the date of birth (Date)
     * @param breed the breed (String)
     * @param weight the weight of the guest (double)
     * @param name the name of the guest (String)
     * @param owner the name of the owner of the guest (String)
     */
    public Guest(Date dob, String breed, double weight,
            String name, String owner) {
        super(dob, breed, weight, name, owner);
    }

    /**
     * parameterized constructor that is used to add the guests to the list.
     * Calls determinFoodType and determineFoodAmount.
     * @param dob the date of birth (Date)
     * @param breed the breed (String)
     * @param weight the weight of the guest (double)
     * @param name the name of the guest (String)
     * @param owner the name of the owner of the guest (String)
     * @param roomNumber the room number where the guest will stay (int)
     * @param checkInDate the date checked in (Date)
     */
    public Guest(Date dob, String breed, double weight, String name, 
            String owner, int roomNumber, Date checkInDate) {
        super(dob, breed, weight, name, owner);
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        
        // call determinFoodType and determineFoodAmount
        determineTypeOfFood();
        determineAmountOfFood();
    }
    
    /**
     * setCheckInDate sets the check in date and calls determinFoodType 
     * and determineFoodAmount during the check in process.
     * @param checkInDate the date checked in (Date)
     */
    public void setCheckInDate(Date checkInDate) {
        //sets checkInDate during check in process to current date
        this.checkInDate = checkInDate;
        
        //call determinFoodType and determineFoodAmount
        determineTypeOfFood();
        determineAmountOfFood();
    }
    
    /**
     * setRoomNumber sets roomNumber during check in process to the 
     * room found for the guest
     * @param roomNumber
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    /**
     * determineTypeOfFood uses the age of the dog to assign puppy,
     * adult, or mature. It is called by the parameterized constructor that
     * has the room number and check in date as parameters, as well as by
     * setCheckInDate(). Date class includes a method difference(Date) 
     * that will return the count of days between the two dates. You simply 
     * turn that in to the correct number of years.
     */
    private void determineTypeOfFood() {
        double years = 0;
        years = (checkInDate.difference(birthDate))/365.0;
        
        if(years > 0.12 && years <= 1.5) { // puppy
            typeOfFood = "puppy";
        } else if (years > 1.5 && years <= 7) { // adult
            typeOfFood = "adult";
        } else { // mature
            typeOfFood = "mature";
        }
    }
    
    /**
     * determineAmountOfFood uses uses the weight of the dog to calculate 
     * the amount at a rate of .25 oz per pound. This should be called by the
     * parameterized constructor that has the room number and 
     * checkInDate as part of it’s parameter list 
     * as well as by setCheckInDate().
     */
    private void determineAmountOfFood() {
        amountOfFood = weight * 0.25;
    }

    /**
     * getTypeOfFood gets the type of food for the feeding order
     * @return the String type of food
     */
    public String getTypeOfFood() {
        return typeOfFood;
    }

    /**
     * getAmountOfFood gets the amount of food for the feeding order
     * @return the double amount of food
     */
    public double getAmountOfFood() {
        return amountOfFood;
    }

    /**
     * getRoomNumber gets the room number for the feeding order 
     * @return the int room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }
    
    /**
     * getCheckInDate gets the check in date for the Hotel's check out process
     * @return the Date for the check in date
     */
    public Date getCheckInDate() {
        return checkInDate;
    }
    
    /**
     * toString overrides and formats the String to return
     * @return a formatted String for the guest's info
     */
    @Override
    public String toString() {
        /* should print out the guest information in the same format
        and same order that it was read in from the file. */
        
        StringBuilder str = new StringBuilder();
        String eol = System.lineSeparator();
        
        // dob string
        str.append(this.birthDate.getMonth()).append("\t");
        str.append(this.birthDate.getDay()).append("\t");
        str.append(this.birthDate.getYear()).append("\t");
        str.append(breed).append(eol);
        
        // weight, name, owner, room string
        str.append(weight).append("\t");
        str.append(name).append("\t");
        str.append(owner).append("\t");
        str.append(roomNumber).append("\t");
        
        // check in date string
        str.append(this.checkInDate.getMonth()).append("\t");
        str.append(this.checkInDate.getDay()).append("\t");
        str.append(this.checkInDate.getYear()).append("\t").append(eol);
        
        return str.toString();
    }
    
    /**
     * compareTo overrides and compares room numbers. It then returns the
     * appropriate value.
     * @return an int for the compared room numbers
     */
    @Override
    public int compareTo(Dog that) {
        // some constants for clarity
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;        
        // shouldn't be any null objects, but if there are
        // put them at the end
        if (that == null)
            return AFTER; 
        //this optimization is usually worthwhile, and can
        //always be added - if the addresses are the same... they are equal
        if (this == that) 
            return EQUAL;
        // now we will compare room numbers.
        return (int)(this.roomNumber - ((Guest)that).roomNumber);
    }
   
}
