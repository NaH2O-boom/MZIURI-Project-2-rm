package com.mziuri;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

import static com.mziuri.UserServlet.users;

public class MessageServlet extends HttpServlet {
    public static void findUsersMessages(String userName, String password, HttpServletResponse resp) throws IOException, ClassNotFoundException {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/messenger", "root", "Programmer098")){
            try (PreparedStatement st = conn.prepareStatement("select * from users where userName='" + userName + "' and password='" + password + "'")) {
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    try(PreparedStatement st1 = conn.prepareStatement("select * from messages where userName='" + userName + "'")){
                        ResultSet rs1 = st1.executeQuery();
                        if(rs1.next()) resp.getWriter().write(rs1.getString("message"));
                        else resp.getWriter().write("Error 404: Unknown error within server");
                    }
                } else {
                    resp.getWriter().write("Error 402: User not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendUserMessage(String userName, HttpServletResponse resp, String message) throws IOException {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/messenger", "root", "Programmer098")){
            try(PreparedStatement st = conn.prepareStatement("select * from messages where userName='" + userName + "'")){
                ResultSet rs = st.executeQuery();
                if(rs.next()) message = message + "\n" + rs.getString(1);
                try (PreparedStatement st1 = conn.prepareStatement("update messages set message ='" + message + "' where userName ='" + userName + "'")) {
                    int affected_rows = st1.executeUpdate();
                    if (affected_rows > 0) {
                        resp.getWriter().write("Message successfully sent to user:" + userName);
                    } else {
                        resp.getWriter().write("Error 403: User not found");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("param1");
        String password = req.getParameter("param2");
        try {
            findUsersMessages(userName, password, resp);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String userName = req.getParameter("param1");
        String message = req.getParameter("param2");
        MessageValidator validate = MessageValidator.getInstance();
        validate.setMessage(message);
        if(!message.trim().isEmpty() && validate.isValid()) sendUserMessage(userName, resp, message);
        else {
            resp.getWriter().write("Error 302: Invalid message");
        }
    }
}
