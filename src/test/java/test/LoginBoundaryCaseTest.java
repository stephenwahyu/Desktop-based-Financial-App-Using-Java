/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raven.modal.demo.auth.Login;
import raven.modal.demo.model.ModelUser;

import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

public class LoginBoundaryCaseTest {

    private Login login;
    private List<ModelUser> mockUserList;

    @BeforeEach
    void setUp() {
        login = new Login();
        mockUserList = new ArrayList<>();
    }

    @Test
    void testLongUsername() {
        String longUsername = "user_" + "a".repeat(1000);
        String password = "boundaryPass";
        ModelUser longUser = new ModelUser(longUsername, BCrypt.hashpw(password, BCrypt.gensalt()), "BOUNDARY");

        mockUserList.add(longUser);

        boolean result = login.validateUserCredentials(longUsername, password, mockUserList);
        assertTrue(result);
        assertEquals("BOUNDARY", Login.role);
    }
}

