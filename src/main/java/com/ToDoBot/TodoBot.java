package com.ToDoBot;

import com.ToDoBot.service.impl.TaskService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TodoBot extends TelegramLongPollingBot {

    TaskService taskService;

    //Автоматом сделалось без него не работает1
    @Override
    public String getBotUsername() {
        return "TodoListHelperBot";
    }

    @Override
    public String getBotToken() {
        return "7813592997:AAFkxkZSJxXWp9HTJZB28k4gigsQu79iXjw";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/start")) {
                sendMessage(chatId, "Добро пожаловать в To-Do List Bot!");
            } else if (messageText.startsWith("/addtask")) {
                String task = messageText.substring(5).trim();
                if (!task.isEmpty()) {
                    taskService.addTask(task);
                    sendMessage(chatId, "Задача успешно добавлена!");
                } else {
                    sendMessage(chatId, "Укажите задачу после команды /addtask.");
                }
                //Я чувствую себя дебилом мне половину сделала idea, но я не понимаю нахуя тут catch
                // Тыкни в edittask проверь тоже там1111
            } else if (messageText.startsWith("/edittask")) {
                String[] parts = messageText.substring(5).trim().split(" ", 2);
                if (parts.length == 2) {
                    try {
                        Long taskId = Long.parseLong(parts[0]);
                        String newTaskDescription = parts[1];
                        boolean isEdited = taskService.editTask(taskId, newTaskDescription);

                        if (isEdited) {
                            sendMessage(chatId, "Задача успешно отредактирована!");
                        } else {
                            sendMessage(chatId, "Задача с ID " + taskId + " не найдена.");
                        }
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Неверный формат ID. Пожалуйста, введите корректное число.");
                    }
                } else {
                    sendMessage(chatId, "Неверный формат команды. Используйте: /edittask <ID> <новое описание>");
                }
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