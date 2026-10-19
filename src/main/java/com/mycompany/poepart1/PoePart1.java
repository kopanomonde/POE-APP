/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepart1;

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
             
System.out.println(user.returnLoginStatus(isLoggedIn));
 
// Messages can only be sent if the user logged in successfully
if (isLoggedIn) {
    Message.input = scanner; // share the same scanner
    System.out.println("Welcome to QuickChat.");
 
    System.out.print("How many messages do you wish to enter? ");
    int maxMessages = Integer.parseInt(scanner.nextLine().trim());
    int entered = 0;
    boolean running = true;
 
    while (running) {
        System.out.println("\n1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        String option = scanner.nextLine().trim();
 
        if (option.equals("1")) {
            if (entered >= maxMessages) {
                System.out.println("You have already entered all " + maxMessages + " messages.");
                continue;
            }
 
            // recipient
            String recipient;
            while (true) {
                System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                recipient = scanner.nextLine().trim();
                String result = new Message(entered, recipient, "x").checkRecipientCell();
                System.out.println(result);
                if (result.equals("Cell phone number successfully captured.")) {
                    break;
                }
            }
 
            // message text
            String text;
            while (true) {
                System.out.print("Enter your message: ");
                text = scanner.nextLine();
                if (text.trim().isEmpty()) {
                    System.out.println("Message cannot be empty.");
                } else if (text.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters.");
                } else {
                    System.out.println("Message sent");
                    break;
                }
            }
 
            Message msg = new Message(entered, recipient, text);
            System.out.println(msg.messageIDStatus());
 
            // the user chooses: send, disregard or store
            String result = msg.sentMessage();
            System.out.println(result);
 
            if (result.equals("Message successfully sent.")) {
                System.out.println("Message ID: " + msg.messageID);
                System.out.println("Message Hash: " + msg.messageHash);
                System.out.println("Recipient: " + msg.recipientCell);
                System.out.println("Message: " + msg.messageText);
            }
            entered++;
 
        } else if (option.equals("2")) {
            System.out.println("Coming Soon.");
 
        } else if (option.equals("3")) {
            running = false;
 
        } else {
            System.out.println("Please choose 1, 2 or 3.");
        }
    }
 
    System.out.println("Total messages sent: " + Message.returnTotalMessages());
}
 
            
            
            if (isLoggedIn) {
                System.out.println("Welcome " + user.firstName + ", " + user.lastName + " it is great to see you again.");
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }
        }
    }
}
      
