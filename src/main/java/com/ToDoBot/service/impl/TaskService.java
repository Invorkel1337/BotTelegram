package com.ToDoBot.service.impl;

import com.ToDoBot.dto.TaskData;
import com.ToDoBot.entity.Task;
import com.ToDoBot.repository.TaskRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public boolean editTask(Long id, String newDescription) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Задача с ID " + id + " не найдена."));
        task.setTaskDescription(newDescription);
        taskRepository.save(task);
        return true;
    }
    public void deleteTask(long id){
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Такого ID нет");
        }
   }

    private TaskData convertToDTO(Task task) {
        TaskData dto = new TaskData();
        dto.setId(task.getId());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setIsCompleted(task.isCompleted());
        return dto;
    }
}

