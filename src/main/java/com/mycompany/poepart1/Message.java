package com.mycompany.poepart1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Student
 */
import java.util.Scanner;
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
   public static ArrayList<Message> sentMessages = new ArrayList<>();
public static ArrayList<Message> storedMessages = new ArrayList<>();
public static Scanner input = new Scanner(System.in);
   
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
       return  messageID != null &&  messageID.length()<=10;
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
       public String messageIDStatus() {
    return "Message ID generated: " + messageID;
}

public String sentMessage() {
    int choice = 0;
    while (choice < 1 || choice > 3) {
        System.out.println("Choose an option:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        try {
            choice = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 0;
        }
    }

    String result = sentMessage(choice);

    if (choice == 2) {
        System.out.println(result);
        String answer = input.nextLine().trim();
        if (answer.equals("0")) {
            return "Message deleted.";
        }
    }
    return result;
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
       public void storeMessage() {
        storedMessages.add(this);
        StringBuilder json = new StringBuilder("[\n");
        for (int count = 0; count < storedMessages.size(); count++) {
            Message m = storedMessages.get(count);
            json.append("  {\n");
            json.append("    \"messageID\": \"").append(escape(m.messageID)).append("\",\n");
            json.append("    \"messageHash\": \"").append(escape(m.messageHash)).append("\",\n");
            json.append("    \"recipient\": \"").append(escape(m.recipientCell)).append("\",\n");
            json.append("    \"message\": \"").append(escape(m.messageText)).append("\"\n");
            json.append("  }").append(count < storedMessages.size() - 1 ? "," : "").append("\n");
        }
        json.append("]");
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Could not save the message: " + e.getMessage());
        }
    }
 
    private static String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    } 
}
