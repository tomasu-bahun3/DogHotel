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
 * Class Description: A class representing the hotel/kennel for the dogs
 * Project Name: Dog Hotel
 * Due Date: 10/19/19
 * Depends on:
 * Extends: NONE
 * Implements:
 * @author thomas
 */
public class Hotel {
    
    /**
     * String representing the hotel's name
     */
    private String hotelName;
    /**
     * double representing the daily rate
     */
    private double dailyRate;
    /**
     * int for the capacity of the hotel
     */
    private int capacity;
    /**
     * the ArrayList of Guests for the hotel
     */
    private ArrayList<Guest> guests = new ArrayList<>();

    /**
     * parameterized constructor
     * @param hotelName the name of the hotel
     * @param dailyRate the daily rate
     * @param capacity the capacity of the hotel
     */
    public Hotel(String hotelName, double dailyRate, int capacity) {
        this.hotelName = hotelName;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
    }

    /**
     * addGuest method to check capacity against occupancy and
     * if there is room, adds the guest to the list, sorts it, and
     * returns true. Otherwise, it returns false. Called by client
     * when the file is read, but also called by checkInGuest()
     * @param g the guest in question
     * @return the boolean true or false if there is or is not space
     */
    public boolean addGuest(Guest g) {
        if(guests.size() < capacity) {
            
            guests.add(g);
            
            Collections.sort(guests); // sorts the ArrayList after adding
            
            return true; // there was space and added guest
        }
        return false; // no space
    }
    
    /**
     * findEmptyRoom method that checks the capacity against the occupancy
     * to determine if there is a vacancy. If there is no vacancy, it returns
     * -1. Otherwise it will search the ArrayList for the first roomNumber
     * that doesn't match the index using a simple linear search.
     * @return the int -1 or the index for the roomNumber
     */
    private int findEmptyRoom() {
        int index = 0;
        if(guests.size() >= capacity) {
            index = -1;
        }
        while(index < guests.size() 
                && guests.get(index).getRoomNumber() == index) {
            index++;
        }
        return index;
    }
    
    /**
     * checkINGuest calls findEmptyRoom. If the roomNumber is valid, it
     * sets the roomNumber and check in date in the GUest passed in.
     * It then calls addGuest and returns the room number. The driver
     * checks for validity of the room number to report to the client.
     * @param g the guest in question
     * @param today today's date passed from driver
     * @return the room number where the guest was checked in
     */
    public int checkInGuest(Guest g, Date today) {
        int room = findEmptyRoom();
        if(room > -1) {
            g.setCheckInDate(today);
            g.setRoomNumber(room);
            addGuest(g);
        } 
        return room;
    }
    /**
     * findGuest uses a linear search to look for the owner's name
     * in the array of guests. It returns the index within the ArrayList
     * where it is found or a -1
     * @param ownerLast the owner's last name of the guest
     * @return the index of the room number where the guest 
     * was checked in or -1 if not found
     */
    private int findGuest(String ownerLast) {
        int index = 0;
        while(index < guests.size() && 
                !(guests.get(index).getOwner().equals(ownerLast))) {
            index++;
        }
        if(index == guests.size()) {
            index = -1;
        }
        return index;
    }
    
    /**
     * checkOutGuest calls findGuest. If the roomNUmber is valid (!=-1), it
     * creates a String invoice that contains the dog's name, roomNumber,
     * and the charges. It then removes that element from the ArrayList.
     * Otherwise it returns the String owner not found.
     * @param ownerLast a String of the owner's last name
     * @param today a Date for today
     * @return a String with the checked out guest's information,
     * including their name, room number, and charges.
     */
    public String checkOutGuest(String ownerLast, Date today) {
        String notFound = "owner not found";
        Guest guest = guests.get(findGuest(ownerLast));
        StringBuilder str = new StringBuilder();
        String eol = System.lineSeparator();
        
        while(findGuest(ownerLast) > -1) {
            str.append(guest.getName()).append("\t");
            str.append(guest.getRoomNumber()).append("\t");
            str.append(calcCharges(guest, today)).append(eol);
            
            guests.remove(guest); // removes the guest from the ArrayList
            
            return str.toString();
        }
        
        return notFound; // if we got here, the owner wasn't found
    }
    
    /**
     * createFeedingOrder goes through the ArrayList of guests creating
     * Strings in a local array that detail the dog's room number,
     * name, type of food, and amount of food so that the feeding trolley
     * can be prepared. Returns the array of Strings.
     * @return the String[] containing the feeding order
     */
    public String[] createFeedingOrder() {
        String[] feedingOrder = new String[guests.size()];
        StringBuilder str; // do not assign yet
        String eol = System.lineSeparator();
        Guest guest; // do not assign yet
        
        for(int i = 0; i < guests.size(); i++) {
            guest = guests.get(i);
            str = new StringBuilder();
            str.append(String.valueOf(guest.getRoomNumber())).append("\t");
            str.append(guest.getName()).append("\t");
            str.append(guest.getTypeOfFood()).append("\t");
            str.append(guest.getAmountOfFood());
            
            feedingOrder[i] = str.toString();
        }
        
        return feedingOrder;
    }
    
    /**
     * calcCharges uses the daily rate to calculate the charges for
     * the guest at check out. Uses the difference to calculate the
     * number of days.
     * @param g the guest
     * @param today the Date for today
     * @return the double of the calculated charges for check out
     */
    public double calcCharges(Guest g, Date today) {
        return dailyRate * g.getCheckInDate().difference(today);
    }

    /**
     * getOccupancy returns the size of the array list
     * @returns the size of the array list
     */
    public int getOccupancy() {
        return guests.size();
    }
    
    /**
     * getGuestAt returns the guest at the given index within the
     * Array List providing that it is a valid index
     * @param index the index of the guest in question
     * @return the Guest at the specified index
     */
    public Guest getGuestAt(int index) {
        return guests.get(index);
    }
}
