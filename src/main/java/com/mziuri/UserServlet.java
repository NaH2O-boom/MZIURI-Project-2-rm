package com.mziuri;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;



public class UserServlet extends HttpServlet {
    public static ArrayList<UserManager> users = new ArrayList<>();
    private static boolean userExists(UserManager newUser) {
        String newUsername = newUser.getUsername();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/messenger", "root", "Programmer098")) {
            try (PreparedStatement st = conn.prepareStatement("select * from users where userName=?")) {
                st.setString(1, newUsername);
                System.out.println(newUsername);
                try (ResultSet rs = st.executeQuery()) {
                    return !rs.next();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String userName = req.getParameter("param1");
        String password = req.getParameter("param2");

        UserManager user = UserManager.getInstance();

        user.setUsername(userName);
        user.setPassword(password);
            
        if(userExists(user)) {
            System.out.println("hi1");
            if (userName.trim().isEmpty() || password.trim().isEmpty()) {
                System.out.println("hi2");
                resp.getWriter().write("Error 403: fields are empty!");
            }else{
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/messenger", "root", "Programmer098")) {
                    try (PreparedStatement st = conn.prepareStatement("INSERT INTO users(userName, password) VALUES (?, ?)")) {
                        try (PreparedStatement st1 = conn.prepareStatement("INSERT INTO messages(userName, message) VALUES (?, ?)")) {
                            st.setString(1, userName);
                            st.setString(2, password);
                            st1.setString(1, userName);
                            st1.setString(2, "");
                            int affectedRows = st.executeUpdate();
                            int affectedRows1 = st1.executeUpdate();
                            if (affectedRows > 0 && affectedRows1 > 0) {
                                resp.getWriter().write("User registered successfully!");
                            } else {
                                resp.getWriter().write("Error 202: Failed to register for unknown reason.");
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else resp.getWriter().write("Error 403: User already registered!");
    }
}

