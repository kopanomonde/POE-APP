package com.mycompany.poepart1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MessageArrayTest {

    // createD a message with a fixed ID, then sends it (1), disregards it (2) or stores it (3)
    private Message make(int number, String id, String recipient, String text, int flag) {
        Message m = new Message(number, recipient, text);
        m.messageID = id;
        m.messageHash = m.createMessageHash();
        m.sentMessage(flag);
        return m;
    }

    @BeforeEach
    public void setUp() {
        new File("target").mkdirs();
        Message.jsonFile = "target/test-messages.json";   // the real messages.json is never touched
        Message.totalMessagesSent = 0;
Message.totalMessagesSent = 0;
        Message.store = new MessageStore();
        Message.store.jsonFile = Message.jsonFile;

        // the  test data, messages 1 to 5
        make(0, "0000000001", "+27834557896", "Did you get the cake?", 1);
        make(1, "0000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", 3);
        make(2, "0000000003", "+27834484567", "Yohoooo, I am at your gate.", 2);
        make(3, "0838884567", "0838884567", "It is dinner time!", 1);
        make(4, "0000000005", "+27838884567", "Ok, I am leaving without you.", 3);
    }

   @Test
    public void testSentMessagesArrayPopulated() {
        assertArrayEquals(new String[]{"Did you get the cake?", "It is dinner time!"},
                Message.getSentMessageTexts());
    }
    @Test
    public void testLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                Message.longestMessage());
    }

  @Test
    public void testSearchByMessageID() {
        String[] found = Message.searchByMessageID("0838884567");
        assertNotNull(found);
        assertEquals("It is dinner time!", found[1]);
    }

    @Test
    public void testSearchForRecipient() {
        String[] expected = {
            "Where are you? You are late! I have asked you to be on time.",
            "Ok, I am leaving without you."
        };
        assertArrayEquals(expected, Message.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                Message.deleteByHash("00:1:WHERETIME"));
        assertEquals(1, Message.store.getStoredCount());
    }

    @Test
    public void testDeleteHashNotFound() {
        assertEquals("Message hash not found.", Message.deleteByHash("99:9:NOTHERE"));
    }

    @Test
    public void testDisregardedMessagesArray() {
        assertEquals(1, Message.store.getDisregardedCount());
        assertEquals("Yohoooo, I am at your gate.", Message.store.getDisregardedMessage(0));
    }

    @Test
    public void testHashAndIdArraysPopulated() {
        assertEquals(5, Message.store.getMessageIDs().length);
        assertEquals(5, Message.store.getMessageHashes().length);
        assertTrue(Arrays.asList(Message.store.getMessageIDs()).contains("0838884567"));
        assertTrue(Arrays.asList(Message.store.getMessageHashes()).contains("00:4:OKYOU"));
    }

    @Test
    public void testReadStoredMessagesFromJson() {
        Message.store.storedCount = 0;
        Message.loadStoredMessages();
        assertEquals(2, Message.store.getStoredCount());
        assertEquals("Ok, I am leaving without you.", Message.store.getStoredMessage(1));
    }
}