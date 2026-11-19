/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepart1;
import java.util.ArrayList;  
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PoePart1 {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    //getting account creation info from the user 
System.out.println("Please enter your First name: ");
        String firstName = scanner.nextLine();

        System.out.println("Please enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Please enter your cell phone number (+27...): ");
        String cellNumber = scanner.nextLine();

        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        // creating a new account from info given by the user 
        Login user= new Login(firstName, lastName, cellNumber, username, password);
        if (user.checkUserName()) {
            System.out.println("Username successfully captured.");
        } else { 
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }
        if (user.checkPassword()) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }
        if (user.checkCellNumber()) {
            System.out.println("Cell number successfully added.");
        } else {
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }
        if (user.checkUserName() && user.checkPassword() && user.checkCellNumber()) {
            System.out.println("\n--- Login ---");
            System.out.print("Please enter username to login: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Please enter password to login: ");
            String enteredPassword = scanner.nextLine();

    boolean isLoggedIn = user.loginUser(enteredUsername, enteredPassword);
    if (isLoggedIn) {
      System.out.println("Welcome " + user.firstName + ", " + user.lastName + " it is great to see you again.");

Message.input = scanner;  
     Message.sender = user.firstName + " " + user.lastName;   // >>> NEW
     Message.loadStoredMessages();            
         System.out.println("Welcome to QuickChat.");

       boolean running = true;
          while (running) {
           System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
           System.out.println("3) Quit");
              System.out.println("4) Stored Messages");  
            String option = scanner.nextLine().trim();
          if (option.equals("1")) {
            System.out.print("How many messages do you wish to enter? ");
             int maxMessages = Integer.parseInt(scanner.nextLine().trim());

     for (int count = 0; count < maxMessages; count++) {
       System.out.println("\nMessage " + (count + 1) + " of " + maxMessages);

         // recipient
          String recipient;
       while (true) {
       System.out.print("Enter recipient cell number (e.g. +27718693002): ");
          recipient = scanner.nextLine().trim();
         String check = new Message(count, recipient, "x").checkRecipientCell();
           System.out.println(check);
             if (check.equals("Cell phone number successfully captured.")) {
              break;
           }
        }
                         
          String text;
    while (true) {
        System.out.print("Enter your message: ");
         text = scanner.nextLine();
      if (text.trim().isEmpty()) {
       System.out.println("Message cannot be empty.");
        } else if (text.length() > 250) {
    System.out.println("Please enter a message of less than 250 characters.");
     } else {
           break;
          }
       }

       Message msg = new Message(count, recipient, text);
      System.out.println(msg.checkMessageID() ? "Message ID generated: " + msg.messageID : "Invalid Message ID.");


       String result = msg.sentMessage();
         System.out.println(result);

                            
      if (result.equals("Message successfully sent.")) {
             System.out.println("Message ID: " + msg.messageID);
             System.out.println("Message Hash: " + msg.messageHash);
             System.out.println("Recipient: " + msg.recipientCell);
             System.out.println("Message: " + msg.messageText);
       }
   }

   } else if (option.equals("2")) {
         String sentReport = Message.printMessages();
                        System.out.println(sentReport.isEmpty() ? "No messages have been sent yet." : sentReport);

     } else if (option.equals("3")) {
      running = false;
                   
      } else if (option.equals("4")) {
      boolean inStored = true;
      while (inStored) {
        System.out.println("\n--- Stored Messages ---");
       System.out.println("a) Display the sender and recipient of all stored messages");
        System.out.println("b) Display the longest stored message");
       System.out.println("c) Search for a message ID");
         System.out.println("d) Search for all messages for a recipient");
       System.out.println("e) Delete a message using the message hash");
        System.out.println("f) Display a report of all stored messages");
       System.out.println("g) Back to the main menu");
       String sub = scanner.nextLine().trim().toLowerCase();

         if (sub.equals("a")) {
             System.out.println(Message.displaySenderAndRecipient());

   } else if (sub.equals("b")) {
      String longest = Message.longestMessage();
    System.out.println(longest.isEmpty() ? "No stored messages." : "Longest message: " + longest);

} else if (sub.equals("c")) {
            System.out.print("Enter the message ID: ");
            String[] found = Message.searchByMessageID(scanner.nextLine().trim());
            if (found == null) {
                System.out.println("Message ID not found.");
            } else {
                System.out.println("Recipient: " + found[0]);
                System.out.println("Message: " + found[1]);
            }

        } else if (sub.equals("d")) {
            System.out.print("Enter the recipient cell number: ");
            String[] results = Message.searchByRecipient(scanner.nextLine().trim());
            if (results.length == 0) {
                System.out.println("No messages found for that recipient.");
            } else {
                for (String line : results) {
                    System.out.println(line);
                }
            }

      } else if (sub.equals("e")) {
        System.out.print("Enter the message hash: ");
        System.out.println(Message.deleteByHash(scanner.nextLine().trim()));

       } else if (sub.equals("f")) {
        System.out.println(Message.storedReport());

     } else if (sub.equals("g")) {
       inStored = false;

    } else {
     System.out.println("Please choose a, b, c, d, e, f or g.");
   }
  }
       } else {
        System.out.println("Please choose 1, 2 ,3 or 4.");
     }
    }

     System.out.println("Total messages sent: " + Message.returnTotalMessages());

     } else {
    System.out.println("Username or password incorrect, please try again.");
            }
        }
    }
}