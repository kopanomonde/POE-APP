package com.mycompany.poepart1; // keep this the same as your Login.java package
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
public class MessageTest {
 
    // test data from the brief
    Message message1;
    Message message2;
 
    @BeforeEach
    public void reset() {
        
        message1 = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message1.messageID = "0012345678";
        message1.messageHash = message1.createMessageHash();
 
        message2 = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        message2.messageID = "0012345678";
        message2.messageHash = message2.createMessageHash();
 
        Message.totalMessagesSent = 0;
        Message.sentMessages.clear();
        Message.storedMessages.clear();
    }
 
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", message1.checkMessageLength());
    }
 
    @Test
    public void testMessageLengthFailure() {
        Message longMsg = new Message(0, "+27718693002", "a".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                longMsg.checkMessageLength());
    }
 
    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.", message1.checkRecipientCell());
    }
 
    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message2.checkRecipientCell());
    }
 
    @Test
    public void testMessageHashTestCase1() {
        assertEquals("00:0:HITONIGHT", message1.createMessageHash());
    }
 
    @Test
    public void testMessageHashLoop() {
        Message[] messages = {message1, message2};
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};
        for (int i = 0; i < messages.length; i++) {
            assertEquals(expected[i], messages[i].createMessageHash());
        }
    }
 
    @Test
    public void testMessageIDStatus() {
        assertEquals("Message ID generated: 0012345678", message1.messageIDStatus());
    }
 
    @Test
    public void testMessageIDCreated() {
        Message m = new Message(0, "+27718693002", "Hello there");
        assertEquals(10, m.messageID.length());
        assertTrue(m.checkMessageID());
    }
 
    @Test
    public void testSendMessage() {
        assertEquals("Message successfully sent.", message1.sentMessage(1));
    }
 
    @Test
    public void testDisregardMessage() {
        assertEquals("Press 0 to delete the message.", message2.sentMessage(2));
    }
 
    @Test
    public void testStoreMessage() {
        assertEquals("Message successfully stored.", message1.sentMessage(3));
    }
 
    @Test
    public void testTotalMessagesSent() {
        message1.sentMessage(1);  
        message2.sentMessage(2); 
        assertEquals(1, Message.returnTotalMessages());
    }
}