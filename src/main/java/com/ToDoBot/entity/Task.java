package com.ToDoBot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "TASK")
@NoArgsConstructor

public class Task {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /**
     * Описание задачи.
     */
    @Column(name = "task_description")
    private String taskDescription;


    /**
     * Признак завершения задачи.
     */
    @Column(name = "completed")
    private Boolean isCompleted;

    public boolean isCompleted() {
        return isCompleted;
    }
}
