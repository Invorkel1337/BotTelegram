package com.ToDoBot;

import com.ToDoBot.service.impl.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TodoBot extends TelegramLongPollingBot {

    @Autowired
    TaskService taskService;

    //Автоматом сделалось без него не работает11
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
                String task = messageText.substring(8).trim();
                if (!task.isEmpty()) {
                    taskService.addTask(task);
                    sendMessage(chatId, "Задача успешно добавлена!");
                } else {
                    sendMessage(chatId, "Укажите задачу после команды /addtask.");
                }
            } else if (messageText.startsWith("/edit")) {
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

            } else if (messageText.startsWith("/delete")) {
                String parts = messageText.substring(7).trim();
                if(!parts.isEmpty()) {
                    try {
                        Long longId = Long.parseLong(parts);
                        taskService.deleteTask(longId);
                        sendMessage(chatId, "Задача успешно удалена!");
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "Введите ID задачи");
                    }
                }else{
                    sendMessage(chatId,"Неверный формат команды. Используейте:/delete <ID>");
                }
            }
            //} else if (messageText.startsWith("/listtask")){
            //try {
            //}

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