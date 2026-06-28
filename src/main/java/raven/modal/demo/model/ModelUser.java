package raven.modal.demo.model;

public class ModelUser {
    public ModelUser(String userName, String passWord, String role) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
    }

    public ModelUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    String userName;
    String passWord;
    String role;
}
