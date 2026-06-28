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

public class LoginValidInputTest {

    private Login login;
    private List<ModelUser> mockUserList;

    @BeforeEach
    void setUp() {
        login = new Login();
        ModelUser user1 = new ModelUser("user1", BCrypt.hashpw("pass1", BCrypt.gensalt()), "ADMIN");
        mockUserList = Collections.singletonList(user1);
    }

    @Test
    void testValidUserCredentials() {
        boolean result = login.validateUserCredentials("user1", "pass1", mockUserList);
        assertTrue(result);
        assertEquals("ADMIN", Login.role);
    }
}

