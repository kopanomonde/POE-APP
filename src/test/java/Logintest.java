

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.poepart1.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class Logintest {
    

Login validUser = new Login("Kopano", "Monde", "+27838968976", "kyl_1", "Ch&&sec@ke99!");
    Login invalidUser = new Login("Kopano", "Monde", "08966553", "kyle!!!!!!", "password");

    @Test
    public void testCheckUserNameValid() {
        assertTrue(validUser.checkUserName());
    }

    @Test
    public void testCheckUserNameInvalid() {
        assertFalse(invalidUser.checkUserName());
    }

    @Test
    public void testCheckPasswordSuccess() {
        assertTrue(validUser.checkPassword());
    }

    @Test
    public void testCheckPasswordFailure() {
        assertFalse(invalidUser.checkPassword());
    }

    @Test
    public void testCellNumberValid() {
        assertTrue(validUser.checkCellNumber());
    }

    @Test
    public void testCellNumberInvalid() {
        assertFalse(invalidUser.checkCellNumber());
    }
}