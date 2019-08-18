package hotelcoursework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HotelCoursework {
    
    public static Scanner keyboard = new Scanner(System.in);
    public static int numberOfRooms = 10;
    public static boolean saveCompleted = false; //used for the save feature in multiple functions
    public static Queue queue = new Queue();
    
    public static void main(String[] args) {
    
        Room[] hotel = new Room[(numberOfRooms+1)]; //+1 so the number of rooms (10) will be accurate when skipping the 0
        
        initialize(hotel);
        
        boolean running = true ; //used to loop the menu until the user wishes to exit
        char menuChoice ;
        
        printMenu();
        
        while (running)
        {
            menuChoice = getMenuChoice();
            
            switch (menuChoice) 
            {
                case 'V' : // view all rooms
                    viewAllRooms(hotel); 
                    break;
                case 'A' : // add customers to room
                    addCustomer(hotel);
                    break;
                case 'D' : // remove customer from room
                    removeCustomer(hotel);
                    break;
                case 'S' : // save current room listings
                    save(hotel);
                    break;    
                case 'L' : // load last saved room listings
                    load(hotel);
                    break;
                case 'M' : // shows the menu again
                    printMenu();
                    break;
                case '3' : // shows and removes the top three people in the queue
                    three(queue);
                    break;
                case 'Q' : // quit program
                    running = false ;
                    break;
                case 'B' : // shows the queue
                    displayTheQueue(queue);
                    break;
            }
        }
    }
    
    //assigns all rooms in the hotel array to empty
    private static void initialize(Room hotelRef[]) {
        for (int x = 1; x <= numberOfRooms; x++ )
        {
            hotelRef[x] = new Room();
            hotelRef[x].setName("empty");
        }
    }
    
    //prints out the menu options, this is only done at the beginning and on command to avoid clutter
    private static void printMenu(){
    
        System.out.println();
        System.out.println("-*-*-*-*- Menu -*-*-*-*-");
        System.out.println("V: View all rooms");
        System.out.println("A: Add customer to room");
        System.out.println("D: Remove customer from a room");
        System.out.println("S: Save current room listings");
        System.out.println("L: Load last saved room listings");
        System.out.println("3: Show and remove the first three people in the queue");
        System.out.println("Q: Quit program");
        
    }
    
    //asks for a char choice corresponding to the menu, will run validation and return a menu choice
    private static char getMenuChoice () {
        
        String input;
        char acceptedChar = 'X';
        boolean acceptableInput = false;
        
        System.out.println();
        System.out.print("Please enter your choice from the menu or enter "
                + "\"M\" to show the menu again: ");
        input = keyboard.nextLine();
        
        while (!acceptableInput)
        {
            try 
            {
                acceptedChar = input.charAt(0);
                acceptedChar = Character.toUpperCase(acceptedChar);
                
                if (acceptedChar == 'V' || acceptedChar == 'A' || acceptedChar == 'D' || 
                    acceptedChar == 'S' || acceptedChar == 'L' || acceptedChar == 'M' || 
                    acceptedChar == 'Q' || acceptedChar == '3' || acceptedChar == 'B') 
                    
                    acceptableInput = true ;
                
                else
                {
                    System.out.println("That is an invalid option.");
                    System.out.println();
                    System.out.print("Please enter one of the menu options as listed above: ");
                    input = keyboard.nextLine();
                }

            }
            catch (NumberFormatException e)
            {
                System.out.println("That is an invalid option.");
                System.out.println();
                System.out.print("Please enter one of the menu options as listed above: ");
                input = keyboard.nextLine();
            }
        }
       
        return acceptedChar ;      
    
    }
    
    //runs a loop to print out all the rooms and who is in them (or if they're empty)
    private static void viewAllRooms (Room hotelRef[]) {
        
        System.out.println();
        
        for (int x = 1; x <= numberOfRooms ; x++)
        {
            if (hotelRef[x].getName().equals("empty")) 
                System.out.println("Room " + x + " is empty.");
            else 
                System.out.println("Room " + x + " is currently occupied "
                        + "by " + hotelRef[x].getName() +".");
        } 
        
    }
    
    //adds a customer to a room by asking the room number then a name if the room is empty
    private static void addCustomer (Room hotelRef[]) {

        String input ;
        int roomNumber ;
        
        System.out.println();
        System.out.print("Enter room number to add a customer to (1-10): ");
        input = keyboard.nextLine();
        
        roomNumber = validateRoomNumber(input);
        
        if (!hotelRef[roomNumber].getName().equals("empty")) 
                System.out.println("Room " + roomNumber + " is already occupied by " + hotelRef[roomNumber].getName() + ".");
        else
        {
            System.out.print("Enter the name of the person who will occupy room " + roomNumber + ": ");
            hotelRef[roomNumber].setName(keyboard.nextLine().toUpperCase()); //all text is converted to uppercase to avoid mismatching cases when searching

            System.out.println();
            System.out.println("Room " + roomNumber + " is now occupied by " + hotelRef[roomNumber].getName() + ".");

            queue.addToQueue(hotelRef[roomNumber].getName());
        }
        
    }
    
    //shows currently occupied rooms, asks room number, and confirms when completed or if the room was already empty
    private static void removeCustomer (Room hotelRef[]) {
        
        String input ;
        int roomNumber ;
        
        System.out.println();
        System.out.println("Currently occupied rooms: ");
        
        for (int x = 1; x <= numberOfRooms ; x++)
            if (!hotelRef[x].getName().equals("empty")) System.out.println(x + ": " + hotelRef[x].getName());
        
        System.out.println();
        System.out.print("Enter the room number to empty: ");
        input = keyboard.nextLine();
        
        roomNumber = validateRoomNumber(input);
        
        if (hotelRef[roomNumber].getName().equals("empty"))
        {
            System.out.println();
            System.out.println("Room " + roomNumber + " is already empty.");
        }
        else 
        {
            System.out.println();
            System.out.println(hotelRef[roomNumber].getName() + " will now be removed from " + roomNumber + ".");
            hotelRef[roomNumber].setName("empty");
            
            System.out.println();
            System.out.println("Room " + roomNumber + " is now empty.");
        }
   
    }
    
    //writes to a file on my H drive and stores all the occupants
    private static void save (Room hotelRef[]){

        try 
        {
            PrintWriter write = new PrintWriter("H://Software Development Principles II/Hotel Save File.txt");

            for (int x=0; x <= numberOfRooms ; x++){
               write.println(hotelRef[x].getName());
            }
            write.close();
            
            System.out.println();
            System.out.println("Save completed.");
            
            saveCompleted = true ;
        }
        catch (Exception e)
        {
            System.out.println("Save was unable to be completed due to an error.");
        }
    }
     
    //will load the last saved file if saved before otherwise a message is displaed and the load is skipped
    private static void load (Room hotelRef[]){
        
        String[] hotelArray = new String[numberOfRooms+1];
        
        if (!saveCompleted) 
        {
            System.out.println();
            System.out.println("There is no saved data.");
        }
        else
        {

            BufferedReader reader = null;
            String placeHolderString = "";
            ArrayList<String> loadedList = new ArrayList<String>();

            try 
            {   
                reader = new BufferedReader(new FileReader("H://Software Development Principles II/Hotel Save File.txt"));

                for (int x=1; x <= numberOfRooms ; x++) loadedList.add(reader.readLine());
                
                hotelArray = loadedList.toArray(hotelArray);
                
                System.out.println();
                System.out.println("Load completed. The rooms have been updated.");
            } 
            catch (Exception e) 
            {
                System.out.println();
                System.out.println("The save could not be loaded due to an error.");
            }
        }
        
        for (int x=1; x<= numberOfRooms ; x++) hotelRef[x].setName(hotelArray[x]);
        
    }
    
    private static void three (Queue queueRef){
        
        queue.topThree();
        
    }
    
    private static void displayTheQueue (Queue queueRef){
        
        queue.displayQueue();
        
    }
    
    //will confirm the text entered is a integer between 1 and 10, will repeat process until satsfied
    private static int validateRoomNumber (String input) {

        int acceptedNumber = 0;
        boolean acceptableInput = false;
        
        while (!acceptableInput)
        {
            try 
            {
                acceptedNumber = Integer.parseInt(input);
                
                if (acceptedNumber >= 1 && acceptedNumber <= numberOfRooms) acceptableInput = true ;
                else
                {
                    System.out.println("That is an invalid option.");
                    System.out.println();
                    System.out.print("Please enter a room number (1-10): ");
                    input = keyboard.nextLine();
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("That is an invalid option.");
                System.out.println();
                System.out.print("Please enter a room number (1-10): ");
                input = keyboard.nextLine();
            }
        }
        
        return acceptedNumber;
        
    }
    
}