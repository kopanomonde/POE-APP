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
            
            // Output Login Status using System.out.println
            if (isLoggedIn) {
                System.out.println("Welcome " + user.firstName + ", " + user.lastName + " it is great to see you again.");
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }
        }

        scanner.close();
    }
}
      
