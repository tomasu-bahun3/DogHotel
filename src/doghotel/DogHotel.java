/** *********************************************************************
 * Revision History (newest first)
 ***********************************************************************
 * 10/20/19 - Finished project - Thomas Bahun
 * 10/19/19 - testing and debugging - Thomas Bahun
 * 10/18/19 - Worked on project Dog Hotel and driver - Thomas Bahun
 * 1/2018 - AA - rewrote without static methods
 * 2014 - AA - Wrote the original driver
 ********************************************************************* */
package doghotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The driver for the Dog Hotel problem.  <br>
 * To model several good strategies for students.<br>
 * <ul>
 * <li>Creates a Hotel object, reads the file</li>
 * <li>Reads the input file</li>
 * <li>Displays a menu</li>
 * <li>Validates input</li>
 * <li>Executes based on user input using a switch statement</li>
 * </ul>
 * @author aapplin
 */
public class DogHotel {
    private Hotel hotel;
    /**
     * A method that reads the file and loads the guests. It does so
     * by taking each int, String, etc. and assigning to a var that
     * are then used to instantiate Date objects and Guest objects. Lastly
     * it adds the guests to the ArrayList in Hotel.
     * @param fileName an input file name
     */
    // You write this one ... and add your name to the revision history
    public void readGuestFile(String fileName) {
        try {
            Scanner inFile = new Scanner(new FileReader(fileName));
            // declare variables and read the file in right here
            String name, breed, owner;
            double weight;
            Date dob, checkInDate;
            int dob1, dob2, dob3, checkIn1, checkIn2, checkIn3, roomNum;
            Guest g; // declare, do not create
            while (inFile.hasNext()) {
                dob1 = inFile.nextInt();
                dob2 = inFile.nextInt();
                dob3 = inFile.nextInt();
                
                dob = new Date(dob1, dob2, dob3);
                
                breed = inFile.nextLine();
                weight = inFile.nextDouble();
                owner = inFile.next();
                name = inFile.next();
                roomNum = inFile.nextInt();
                checkIn1 = inFile.nextInt();
                checkIn2 = inFile.nextInt();
                checkIn3 = inFile.nextInt();
                
                checkInDate = new Date(checkIn1, checkIn2, checkIn3);
                
                g = new Guest(dob, breed, weight, name, owner, 
                        roomNum, checkInDate);
                
                hotel.addGuest(g);
            }
            inFile.close();
        } catch (FileNotFoundException ex) {
            // we catch it and print an error message
            System.out.println("File data.txt not found");
            // and exit in a controlled manner
            System.exit(1);
        }
    }

    /**
     * This method is complete, DO NOT CHANGE
     * Displays a menu for the user to select from.
     */
    public void displayMenu() {
        System.out.println("=================================");
        System.out.println("|  1.  Perform Checkin.         |");
        System.out.println("|  2.  Perform Checkout.        |");
        System.out.println("|  3.  Print the feeding order. |");
        System.out.println("|  4.  Backup.                  |");
        System.out.println("|  5.  Perform end of day.      |");
        System.out.println("=================================");
        System.out.println();
    }

    /**
     * This method is complete, DO NOT CHANGE
     * Gets an integer from the user in the range of min to max
     *
     * @param in a Scanner object to read from the keyboard
     * @param low the lowest acceptable number for input
     * @param high the highest acceptable number for input
     * @param message the kind of input we are looking for
     * @return a valid integer input
     */
    //
    public int getValidIntInput(Scanner in, int low, int high,
            String message) {
        int choice;

        do {
            System.out.print("Enter a " + message + " between " + low
                    + " and " + high + ":  ");
            // getting input it ALWAYS risky. 
            try{                
                choice = in.nextInt();
            }catch(InputMismatchException ex){
                choice = low - 1; // puts it outside of the acceptable range
            }
            
        } while (choice < low || choice > high);
        return choice;
    }

    /**
     * This method is complete, DO NOT CHANGE
     * Acquires all of the guest information and creates a guest.
     *
     * @param in A Scanner Object
     * @return a Guest object with everything but a room number and check in
     * date.
     */
    public Guest getGuestInfo(Scanner in) {
        System.out.print("Enter the owner's name: ");
        String owner = in.next();
        System.out.print("Enter the dog's name: ");
        String name = in.next();

        System.out.println("Please enter the dog's breed:  ");
        in.nextLine(); // throw away the end of line character
        // before using nextLine() to get a string with embedded spaces
        String breed = in.nextLine();
        System.out.println("Enter the dog's date of birth ");
        int month = getValidIntInput(in, 1, 12, "a month");
        int day = getValidIntInput(in, 1, 31, "a day");
        int year = getValidIntInput(in, 1970, 2525, "a year");
        System.out.print("Enter the dog's weight: ");
        double weight = in.nextDouble();
        Guest guest = new Guest(new Date(month, day, year), breed, weight,
                name, owner);
        return guest;
    }

    /**
     * This method is complete, DO NOT CHANGE
     * We need this information in two instances: checking a guest in and
     * checking a guest out, so making it a method keeps from repeating the 
     * same code.
     *
     * @param in A Scanner Object
     * @return a String
     */
    
    public String getOwner(Scanner in) {
        System.out.print("Enter the owner's name: ");
        String owner = in.next();
        return owner;
    }

     /**
     * This method is complete DO NOT CHANGE Backs up the current hotel guests
     * into a data file. Since the computer system is powered down at night, we
     * need to write the current guests out to a file that can be read in the
     * next morning. *
     *
     * @param fileName the fileName that we are writing to.
     */
    //This method is complete, DO NOT CHANGE    
    public void backup(String fileName, Date today) {
        String date = today.toString();
        date = date.replace('/', '_');
        String front = fileName.substring(0, fileName.indexOf('.'));
        String end = fileName.substring(fileName.indexOf('.'));
        fileName = front + date + end;
        try {
            PrintStream out = new PrintStream(new File(fileName));
            for (int i = 0; i < hotel.getOccupancy(); i++) {
                out.println(hotel.getGuestAt(i));
            }
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Getting an error we shouldn't get."
                    + " Is the folder write protected?");
        }
    }

    /**
     * Driver for the DogHotel project. 
     *
     * @param args the command line arguments
     */
    //This method is complete, DO NOT CHANGE
    public void run(String[] args) {
        hotel = new Hotel("Doggie Ritz", 15.00, 25);
        int month, 
            day, 
            year;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter today's date as mm dd yyyy ");
        month = getValidIntInput(in, 1, 12, "a month");
        day   = getValidIntInput(in, 1, 31, "a day");
        year  = getValidIntInput(in, 1970, 2525, "a year");
        Date today = new Date(month, day, year);
        readGuestFile(args[0]);
        boolean done = false; // we just started!

        while (!done) {
            displayMenu();
            int choice = getValidIntInput(in, 1, 5, "menu choice");
            switch (choice) {
                case 1:
                    Guest guest = getGuestInfo(in);
                    int room = hotel.checkInGuest(guest, today);
                    if (room == -1) {
                        System.out.println("No room available.");
                    } else {
                        System.out.println("Checked into room " + room);
                    }
                    break;
                case 2:
                    String owner = getOwner(in);
                    System.out.println(hotel.checkOutGuest(owner, today));
                    break;
                case 3:
                    String[] list = hotel.createFeedingOrder();
                    for (int i = 0; i < list.length; i++) {
                        System.out.println(list[i]);
                    }
                    break;
                case 4:
                    backup(args[1], today);
                    break;
                case 5:
                    backup(args[1], today);
                    done = true;
            } // end switch
        } // end while ! done
    } // end run() 

    /**
     * Main method. 
     *
     * @param args the command line arguments
     */
    //This method is complete, DO NOT CHANGE
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("usage prog inFile outFile");
            System.exit(1);
        }
        DogHotel driver = new DogHotel();
        driver.run(args);
    }

}
