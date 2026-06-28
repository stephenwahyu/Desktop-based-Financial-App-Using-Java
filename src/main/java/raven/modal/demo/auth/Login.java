package raven.modal.demo.auth;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.mindrot.jbcrypt.BCrypt;
import raven.modal.Toast;
import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelUser;
import raven.modal.demo.report.ReportManager;
import raven.modal.demo.service.ServiceUser;
import raven.modal.demo.system.Form;
import raven.modal.demo.system.FormManager;

import javax.swing.*;
import java.util.List;

public class Login extends Form {

    public static String role;

    public Login() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        chRememberMe = new JCheckBox("Remember me");
        cmdLogin = new JButton("Login");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 35 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:shade($Panel.background,5%);" +
                "[dark]background:tint($Panel.background,5%);");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:shade($Panel.background,10%);" +
                "[dark]background:tint($Panel.background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username or email");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        JLabel lbTitle = new JLabel("Welcome back!");
        JLabel description = new JLabel("Please sign in to access your account");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, ""
                + "foreground:$Label.disabledForeground;");

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Username"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(txtPassword);
//        panel.add(chRememberMe, "grow 0");
        panel.add(cmdLogin, "gapy 10");
        add(panel);

        // event
        cmdLogin.addActionListener((e) -> {
            authenticateUser();
//            String userName = txtUsername.getText().trim();
//            // this is just for example to check admin user :)
//
//            boolean isAdmin = userName.equals("admin");
//            FormManager.login();
        });
    }
    public boolean authenticate(String username, String password, List<ModelUser> userList) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        return validateUserCredentials(username, password, userList);
    }


    private void authenticateUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            Toast.show(this, Toast.Type.WARNING, "Username or password cannot be empty.");
            return;
        }

        try {
            DatabaseConnection.getInstance().connectToDatabase();
            List<ModelUser> userList = ServiceUser.Auth();

            for (ModelUser user : userList) {
                if (username.equals(user.getUserName()) && BCrypt.checkpw(password, user.getPassWord())) {
                    role = user.getRole();

                    txtUsername.setText("");
                    txtPassword.setText("");

                    Toast.show(this, Toast.Type.SUCCESS, "Login successful!");
                    FormManager.login();
                    return;
                }
            }

            Toast.show(this, Toast.Type.ERROR, "Invalid username or password.");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.show(this, Toast.Type.ERROR, "An error occurred during login. Please try again.");
        }
    }



    /**
     * Validates the user's credentials against the provided list.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param userList The list of valid users fetched from the database.
     * @return True if the credentials match a user in the list; otherwise, false.
     */
    public boolean validateUserCredentials(String username, String password, List<ModelUser> userList) {
        for (ModelUser user : userList) {
            if (username.equals(user.getUserName()) && BCrypt.checkpw(password, user.getPassWord())) {
                // Set the role for the authenticated user
                role = user.getRole();
                return true;
            }
        }
        return false;
    }
    




    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
}
