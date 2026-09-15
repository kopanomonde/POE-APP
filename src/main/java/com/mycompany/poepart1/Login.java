/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart1;

/**
 *
 * @author Student
 */
public class Login {
    
 public String firstName;
    public String lastName;
    public String username;
    public String password;
      public String cellNumber;
      
     public Login(String firstName , String  lastName, String username,String password,String  cellNumber ){
        this.firstName=firstName;
        this. lastName= lastName;
      this.username=username;  
        this.password=password; 
        this.cellNumber= cellNumber;
                

    }
     public boolean checkUserName(){
         // if he is no input the method must return flase
         if( username==null){
             return false;
             
         }
         return username.contains("_")&& username.length()<5;
     }
  public boolean checkPassword() {
    if (password == null || password.length() < 8) {
        return false;
    }

    boolean hasCapital = !password.equals(password.toLowerCase());
    boolean hasDigit = password.matches(".*\\d.*");
    boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

    return hasCapital && hasDigit && hasSpecial;
}
     public boolean checkCellNumber(){
          // if the is no input the method must return flase
         if(cellNumber==null){
             return false;
            
         }
         String regex = "^\\+\\d{1,3}\\d{1,10}$";
       return java.util.regex.Pattern.matches(regex, cellNumber);
      //4
     }
     public String registerUser(){
         if(!checkUserName()){
             return "Username is incorrect ; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPassword()) {
            return "Password is weak ; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellNumber()) {
            return "Cell phone number is not correctly inputed does not contain international code.";
        }

        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully added.";
    }
       // 5. Login User
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername != null && enteredPassword != null &&
               enteredUsername.equals(username) &&
               enteredPassword.equals(password);
    }
    // 6. Return Login Status
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + this.firstName + " " + this.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    }

