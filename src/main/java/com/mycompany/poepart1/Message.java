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
}
