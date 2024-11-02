package com.ToDoBot.service.impl;

import com.ToDoBot.dto.TaskData;
import com.ToDoBot.entity.Task;
import com.ToDoBot.repository.TaskRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Сервис для сохранения списка задачи в БД.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskData addTask(String description) {
        Task task = new Task();
        task.setTaskDescription(description);
        task.setIsCompleted(false);
        return convertToDTO(taskRepository.save(task));
    }
    //Хз пока будет работаь нет
    public boolean editTask(Long id, String newDescription) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setTaskDescription(newDescription);
        taskRepository.save(task);
        return false;
    }

    private TaskData convertToDTO(Task task) {
        TaskData dto = new TaskData();
        dto.setId(task.getId());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setIsCompleted(task.isCompleted());
        return dto;
    }
}
