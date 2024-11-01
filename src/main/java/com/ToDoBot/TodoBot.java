package com.ToDoBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TodoBot extends TelegramLongPollingBot {

    //Автоматом сделалось без него не работает
    @Override
    public String getBotUsername() {
        return "TodoListHelperBot";
    }

    @Override
    public String getBotToken() {
        return "7813592997:AAFkxkZSJxXWp9HTJZB28k4gigsQu79iXjw";
    }

    //Я не понял как, тут еще надо написать elif если уже есть сообщениях в чате
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/start")) {
                sendMessage(chatId, "Добро пожаловать в To-Do List Bot!");
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}