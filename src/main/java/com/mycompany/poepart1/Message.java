package com.mycompany.poepart1;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Message {
    // declaring my attributes
    public String messageID;
    public int messageNumber;
    public String recipientCell;
    public String messageText;
    public String messageHash;

    public static MessageStore store = new MessageStore();
    public static int totalMessagesSent = 0;
    public static String jsonFile = "messages.json";
    public static String sender = "Developer";
    public static Scanner input = new Scanner(System.in);

    // used parameters to construct
    public Message(int messageNumber, String recipientCell, String messageText) {
        this.messageNumber = messageNumber;
        this.messageText = messageText;
        this.recipientCell = recipientCell;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public String generateMessageID() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 10000000000L);
        return String.format("%010d", number);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }
public String messageIDStatus() {
        if (checkMessageID()) {
            return "Message ID generated: " + messageID;
        }
        return "Invalid Message ID.";
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
            return "Message disregarded.";
        }
        return result;
    }

    public String sentMessage(int choice) {
        store.jsonFile = jsonFile;
        switch (choice) {
            case 1:
                store.recordSent(this);
                totalMessagesSent = store.getTotalSent();
                return "Message successfully sent.";
            case 2:
                store.recordDisregarded(this);
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public static String printMessages() {
        return store.sentReport();
    }

    public static int returnTotalMessages() {
        return store.getTotalSent();
    }

    public void storeMessage() {
        store.jsonFile = jsonFile;
        store.recordStored(this);
    }

    public static void loadStoredMessages() {
        store.jsonFile = jsonFile;
        store.loadStoredMessages();
    }

    // sender and recipient of all stored messages
    public static String displaySenderAndRecipient() {
        store.sender = sender;
        return store.displaySenderAndRecipient();
    }

    // the longest stored message
    public static String longestMessage() {
        return store.longestMessage();
    }

    // search for a message ID (returns {recipient, message} or null if not found)
    public static String[] searchByMessageID(String id) {
        return store.searchByMessageID(id);
    }

    // all the messages sent or stored for a particular recipient
    public static String[] searchByRecipient(String recipient) {
        return store.searchByRecipient(recipient);
    }

    // delete a stored message using its message hash
    public static String deleteByHash(String hash) {
        store.jsonFile = jsonFile;
        return store.deleteByHash(hash);
    }

    // report with the full details of all stored messages
    public static String storedReport() {
        store.sender = sender;
        return store.storedReport();
    }

    // the texts of everything in the Sent Messages array (used by the unit tests)
    public static String[] getSentMessageTexts() {
        return store.getSentMessages();
    }
}