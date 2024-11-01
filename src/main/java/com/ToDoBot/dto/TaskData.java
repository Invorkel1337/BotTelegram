package com.ToDoBot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskData {

    private Long id;
    private String taskDescription;
    private Boolean isCompleted;
}
