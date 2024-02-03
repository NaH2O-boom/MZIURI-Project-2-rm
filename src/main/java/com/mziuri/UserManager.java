package com.mziuri;

public class UserManager {
    private static UserManager instance = null;
    private String username, password, messages;

    private UserManager() {
        this.username = "default";
        this.password = "default";
        this.messages = "default";
        System.out.println("instance added");
    }
    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMessages() {
        return messages;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
