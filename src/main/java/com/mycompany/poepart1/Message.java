package com.mycompany.poepart1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Student
 */

import java.io.FileWriter;
import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Random;  
 import java.util.regex.Pattern;     
public class Message {
       // declaring my atributes 
    
    public String messageID;
    public int messageNumber;
    public String recipientCell;
    public String messageText;
    public String messageHash;
    
   public static int totalMessagesSent=0;
   public static ArrayList<Message>sentMessage =new ArrayList<>();
   public static ArrayList<Message>storedMessage =new ArrayList<>();
   
      //used parametors to construct 
   public Message(int messageNumber,String recipientCell,String messageText){
   this.messageNumber=messageNumber;
   this.messageText=messageText;
   this.recipientCell=recipientCell;
   this.messageID=generateMessageID();
   this.messageHash=createMessageHash();
   }
   public String generateMessageID(){
       Random random =new Random();
       StringBuilder sb = new StringBuilder ();
       for (int count = 0; count <10;count++){
           sb.append(random.nextInt(10));
       }
       return sb.toString();
   }
   public boolean checkMessageID(){
       return  messageID ! = null &&  messageID.length()<=10;
   }
     public String checkRecipientCell() {
        String regex = "^\\+\\d{1,3}\\d{1,10}$";
        if (recipientCell != null && Pattern.matches(regex, recipientCell)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
      public String checkMessageLength() {
        int length = messageText.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250) + "; please reduce the size.";
    }
       public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        String prefix = messageID.substring(0, 2);
        return (prefix + ":" + messageNumber + ":" + first + last).toUpperCase();
    }
          public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
 
    // all messages sent while the program is running
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipientCell).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }
 
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
}
