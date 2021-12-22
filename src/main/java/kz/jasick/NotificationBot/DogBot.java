package kz.jasick.NotificationBot;

import kz.jasick.NotificationBot.Controllers.SendMailController;
import kz.jasick.NotificationBot.Controllers.AuthorizationController;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class DogBot extends TelegramLongPollingBot {

    public DogBot() {
    }

    @Override
    public String getBotUsername() {
        return "PlatonusNotifcationBot";
    }


    @Override
    public String getBotToken() {
        return "5011028425:AAGlmeCBY8kzFYHQo9RZgIPZskKZT_mhbCg";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.getMessage().hasText()){
            String messageIn=update.getMessage().getText();
            if (messageIn.equals("Log in")){
                String str = "Отправьте логин и пароль в виде \"логин(пробел)пароль\"";
                try {
                    execute(new SendMessage().builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .text(str)
                            .build());
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
            else if(messageIn.equals("Log out")){
                AuthorizationController.logOut(update.getMessage().getChatId());
                try {
                    execute(new SendMessage().builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .text("Log out successful")
                            .build());
                }catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
            else if(messageIn.equals("test")){
                SendMailController sendMailController = new SendMailController();
                sendMailController.sendMail("Изменена оцена по дисциплине какой-то на что-то",Long.parseLong("1"));
            }
            else {
                String[] text = update.getMessage().getText().split(" ");
                if (text.length>=2 && AuthorizationController.logIn(text[0], text[1], update.getMessage().getChatId())) {
                    try {
                        execute(new SendMessage().builder()
                                .chatId(update.getMessage().getChatId().toString())
                                .text("Log in successful")
                                .build());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {


                    SendMessage message = SendMessage.builder()
                            .chatId(update.getMessage().getChatId().toString())
                            .text("неправильный логин или пароль \nКоманды: \nLog in \nLog out")
                            .build();
                    setButtons(message);
                    try {
                        execute(message);

                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

    }}

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Log in"));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Log out"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
