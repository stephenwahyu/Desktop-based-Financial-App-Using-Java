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

public class LoginAdditionalScenarioTest {

    private Login login;
    private List<ModelUser> mockUserList;

    @BeforeEach
    void setUp() {
        login = new Login();

        ModelUser user1 = new ModelUser("user1", BCrypt.hashpw("pass1", BCrypt.gensalt()), "ADMIN");
        ModelUser user2 = new ModelUser("user2", BCrypt.hashpw("pass2", BCrypt.gensalt()), "USER");
        ModelUser user3 = new ModelUser("targetUser", BCrypt.hashpw("targetPass", BCrypt.gensalt()), "TARGET");

        mockUserList = Arrays.asList(user1, user2, user3);
    }

    @Test
    void testMultipleUsersOneMatch() {
        boolean result = login.validateUserCredentials("targetUser", "targetPass", mockUserList);
        assertTrue(result);
        assertEquals("TARGET", Login.role);
    }

    @Test
    void testEmptyUserList() {
        boolean result = login.validateUserCredentials("user1", "pass1", new ArrayList<>());
        assertFalse(result);
    }
}
