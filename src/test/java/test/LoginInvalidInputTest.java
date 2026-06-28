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

public class LoginInvalidInputTest {

    private Login login;
    private List<ModelUser> mockUserList;

    @BeforeEach
    void setUp() {
        login = new Login();
        ModelUser user1 = new ModelUser("user1", BCrypt.hashpw("pass1", BCrypt.gensalt()), "ADMIN");
        mockUserList = Collections.singletonList(user1);
    }

    @Test
    void testInvalidPassword() {
        boolean result = login.validateUserCredentials("user1", "wrongpass", mockUserList);
        assertFalse(result);
    }

    @Test
    void testInvalidUsername() {
        boolean result = login.validateUserCredentials("wronguser", "pass1", mockUserList);
        assertFalse(result);
    }
}
