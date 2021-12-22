package kz.jasick.NotificationBot.Controllers;

import kz.jasick.NotificationBot.DogBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;

public class SendMailController extends DogBot {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    public  void sendMail(String text, Long userId) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Statement statement = connection.createStatement();
            String q1 = "SELECT telegram_chat_id FROM users_telegram WHERE  user_id = '" + userId + "'";
            ResultSet rs1 = statement.executeQuery(q1);

            while (rs1.next()) {
                String chatId = rs1.getString("telegram_chat_id");
                execute(new SendMessage().builder()
                        .chatId(chatId)
                        .text(text)
                        .build());
            }
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
