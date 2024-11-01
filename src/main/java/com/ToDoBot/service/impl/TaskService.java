package com.ToDoBot.service.impl;

import com.ToDoBot.dto.TaskData;
import com.ToDoBot.entity.Task;
import com.ToDoBot.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Сервис для сохранения списка задачи в БД.
 */
public class TaskService {

    private TaskRepository taskRepository;

    public TaskData addTask(String description) {
        Task task = new Task();
        task.setTaskDescription(description);
        task.setIsCompleted(false);

        return convertToDTO(taskRepository.save(task));
    }


    private TaskData convertToDTO(Task task) {
        TaskData dto = new TaskData();
        dto.setId(task.getId());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setIsCompleted(task.isCompleted());
        return dto;
    }
}
