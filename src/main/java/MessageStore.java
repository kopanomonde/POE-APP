package com.mycompany.poepart1;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageStore {
    public static final int MAX_MESSAGES = 100;

    // SENT messages: parallel arrays
    public String[] sentIds = new String[MAX_MESSAGES];
    public String[] sentHashes = new String[MAX_MESSAGES];
    public String[] sentRecipients = new String[MAX_MESSAGES];
    public String[] sentMessages = new String[MAX_MESSAGES];
    public int sentCount = 0;

    // STORED messages: parallel arrays
    public String[] storedIds = new String[MAX_MESSAGES];
    public String[] storedHashes = new String[MAX_MESSAGES];
    public String[] storedRecipients = new String[MAX_MESSAGES];
    public String[] storedMessages = new String[MAX_MESSAGES];
    public int storedCount = 0;

    // DISREGARDED messages: the message text only
    public String[] disregardedMessages = new String[MAX_MESSAGES];
    public int disregardedCount = 0;

    // the Message ID array and the Message Hash array (every message, in the same order)
    public String[] messageIDs = new String[MAX_MESSAGES];
    public String[] messageHashes = new String[MAX_MESSAGES];
    public int idCount = 0;

    public String jsonFile = "messages.json";   // file the stored messages are written to / read from
    public String sender = "Developer";         // name shown as the sender

  
    public boolean recordSent(Message message) {
        if (sentCount >= MAX_MESSAGES) {
            return false;
        }
        sentIds[sentCount] = message.messageID;
        sentHashes[sentCount] = message.messageHash;
        sentRecipients[sentCount] = message.recipientCell;
        sentMessages[sentCount] = message.messageText;
        sentCount++;
        recordIdAndHash(message);
        return true;
    }

    public boolean recordDisregarded(Message message) {
        if (disregardedCount >= MAX_MESSAGES) {
            return false;
        }
        disregardedMessages[disregardedCount] = message.messageText;
        disregardedCount++;
        recordIdAndHash(message);
        return true;
    }

    public boolean recordStored(Message message) {
        if (storedCount >= MAX_MESSAGES) {
            return false;
        }
        storedIds[storedCount] = message.messageID;
        storedHashes[storedCount] = message.messageHash;
        storedRecipients[storedCount] = message.recipientCell;
        storedMessages[storedCount] = message.messageText;
        storedCount++;
        recordIdAndHash(message);
        saveToJson();
        return true;
    }

    // adds the ID and hash to the Message ID and Message Hash arrays
    public void recordIdAndHash(Message message) {
        if (idCount < MAX_MESSAGES) {
            messageIDs[idCount] = message.messageID;
            messageHashes[idCount] = message.messageHash;
            idCount++;
        }
    }

    // ---------------- the JSON file ----------------

  
    public void saveToJson() {
        StringBuilder json = new StringBuilder("[\n");
        for (int count = 0; count < storedCount; count++) {
            json.append("  {\n");
            json.append("    \"messageID\": \"").append(escape(storedIds[count])).append("\",\n");
            json.append("    \"messageHash\": \"").append(escape(storedHashes[count])).append("\",\n");
            json.append("    \"recipient\": \"").append(escape(storedRecipients[count])).append("\",\n");
            json.append("    \"message\": \"").append(escape(storedMessages[count])).append("\"\n");
            json.append("  }").append(count < storedCount - 1 ? "," : "").append("\n");
        }
        json.append("]");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Could not save the message: " + e.getMessage());
        }
    }

    /*
     * Reads the JSON file back INTO the stored arrays.
     * Source: Files.readAllBytes from the Oracle Java documentation
     * (https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html)
     * and Pattern / Matcher (https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html)
     */
    public void loadStoredMessages() {
        storedCount = 0;
        Path path = Paths.get(jsonFile);
        if (!Files.exists(path)) {
            return;
        }
        try {
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile(
                    "\"messageID\":\\s*\"(.*?)\",\\s*\"messageHash\":\\s*\"(.*?)\","
                    + "\\s*\"recipient\":\\s*\"(.*?)\",\\s*\"message\":\\s*\"(.*?)\"\\s*\\}",
                    Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find() && storedCount < MAX_MESSAGES) {
                storedIds[storedCount] = unescape(matcher.group(1));
                storedHashes[storedCount] = unescape(matcher.group(2));
                storedRecipients[storedCount] = unescape(matcher.group(3));
                storedMessages[storedCount] = unescape(matcher.group(4));

                if (indexOf(messageIDs, idCount, storedIds[storedCount]) == -1 && idCount < MAX_MESSAGES) {
                    messageIDs[idCount] = storedIds[storedCount];
                    messageHashes[idCount] = storedHashes[storedCount];
                    idCount++;
                }
                storedCount++;
            }
        } catch (IOException e) {
            System.out.println("Could not read the stored messages: " + e.getMessage());
        }
    }

    public static String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static String unescape(String text) {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < text.length(); count++) {
            char c = text.charAt(count);
            if (c == '\\' && count + 1 < text.length()) {
                sb.append(text.charAt(count + 1));
                count++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static int indexOf(String[] array, int count, String value) {
        for (int index = 0; index < count; index++) {
            if (array[index].equalsIgnoreCase(value)) {
                return index;
            }
        }
        return -1;
    }

    public static void removeAt(String[] array, int count, int index) {
        for (int position = index; position < count - 1; position++) {
            array[position] = array[position + 1];
        }
        array[count - 1] = null;
    }

    public String sentReport() {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < sentCount; count++) {
            sb.append("Message ID: ").append(sentIds[count]).append("\n");
            sb.append("Message Hash: ").append(sentHashes[count]).append("\n");
            sb.append("Recipient: ").append(sentRecipients[count]).append("\n");
            sb.append("Message: ").append(sentMessages[count]).append("\n\n");
        }
        return sb.toString();
    }

    // sender and recipient of all stored messages
    public String displaySenderAndRecipient() {
        if (storedCount == 0) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < storedCount; count++) {
            sb.append("Sender: ").append(sender).append(" | Recipient: ")
              .append(storedRecipients[count]).append("\n");
        }
        return sb.toString();
    }

    // the longest stored message
    public String longestMessage() {
        if (storedCount == 0) {
            return "";
        }
        String longest = storedMessages[0];
        for (int count = 1; count < storedCount; count++) {
            if (storedMessages[count].length() > longest.length()) {
                longest = storedMessages[count];
            }
        }
        return longest;
    }

    // search for a message ID in the sent and stored arrays
    // returns {recipient, message}, or null if the ID is not found
    public String[] searchByMessageID(String id) {
        int index = indexOf(sentIds, sentCount, id);
        if (index != -1) {
            return new String[]{sentRecipients[index], sentMessages[index]};
        }
        index = indexOf(storedIds, storedCount, id);
        if (index != -1) {
            return new String[]{storedRecipients[index], storedMessages[index]};
        }
        return null;
    }

    public String[] searchByRecipient(String recipient) {
        String[] found = new String[MAX_MESSAGES * 2];
        int foundCount = 0;
        for (int count = 0; count < sentCount; count++) {
            if (sentRecipients[count].equals(recipient)) {
                found[foundCount] = sentMessages[count];
                foundCount++;
            }
        }
        for (int count = 0; count < storedCount; count++) {
            if (storedRecipients[count].equals(recipient)) {
                found[foundCount] = storedMessages[count];
                foundCount++;
            }
        }
        return Arrays.copyOf(found, foundCount);
    }

    // delete a stored message using its message hash
    public String deleteByHash(String hash) {
        int index = indexOf(storedHashes, storedCount, hash);
        if (index == -1) {
            return "Message hash not found.";
        }
        String deletedText = storedMessages[index];
        String deletedId = storedIds[index];

        removeAt(storedIds, storedCount, index);
        removeAt(storedHashes, storedCount, index);
        removeAt(storedRecipients, storedCount, index);
        removeAt(storedMessages, storedCount, index);
        storedCount--;

        int idIndex = indexOf(messageIDs, idCount, deletedId);
        if (idIndex != -1) {
            removeAt(messageIDs, idCount, idIndex);
            removeAt(messageHashes, idCount, idIndex);
            idCount--;
        }

        saveToJson();
        return "Message: \"" + deletedText + "\" successfully deleted.";
    }

    public String storedReport() {
        if (storedCount == 0) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < storedCount; count++) {
            sb.append("Message ID: ").append(storedIds[count]).append("\n");
            sb.append("Message Hash: ").append(storedHashes[count]).append("\n");
            sb.append("Sender: ").append(sender).append("\n");
            sb.append("Recipient: ").append(storedRecipients[count]).append("\n");
            sb.append("Message: ").append(storedMessages[count]).append("\n\n");
        }
        return sb.toString();
    }

    public int getTotalSent() {
        return sentCount;
    }

    public String[] getSentMessages() {
        return Arrays.copyOf(sentMessages, sentCount);
    }

    public int getStoredCount() {
        return storedCount;
    }

    public String getStoredMessage(int index) {
        return storedMessages[index];
    }

    public int getDisregardedCount() {
        return disregardedCount;
    }

    public String getDisregardedMessage(int index) {
        return disregardedMessages[index];
    }

    public String[] getMessageIDs() {
        return Arrays.copyOf(messageIDs, idCount);
    }

    public String[] getMessageHashes() {
        return Arrays.copyOf(messageHashes, idCount);
    }
}