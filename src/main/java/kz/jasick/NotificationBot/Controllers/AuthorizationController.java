package kz.jasick.NotificationBot.Controllers;

import org.springframework.stereotype.Controller;

import java.sql.*;

@Controller
public class AuthorizationController {
    private static final String URL="jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME ="postgres";
    private static final String PASSWORD="root";

    public static boolean logIn(String login,String password,Long chatId) {

        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement statement = connection.createStatement();
            String q1 = "SELECT id FROM users WHERE  login = '" + login + "'  " + "   and password = '" + password + "'";
            ResultSet rs1 = statement.executeQuery(q1);

            if (rs1.next()) {
                int userId= rs1.getInt("id");
                String q2 = "INSERT into users_telegram VALUES (DEFAULT,"+userId+","+chatId+")";
                int x = statement.executeUpdate(q2);
                return true;
            } else return false;


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();


        }

    }
    public static void logOut(Long chatId) {

        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement statement = connection.createStatement();
            String q1 = "Delete from users_telegram where telegram_chat_id='"+chatId+"'";
            int x = statement.executeUpdate(q1);



        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();


        }

    }
}