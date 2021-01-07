package com.cybertek.service;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;

import java.util.List;

public interface TaskService {
    //I need crud operators
    TaskDTO findById(Long taskId);
    List<TaskDTO> listAllTasks();
    Task save(TaskDTO taskDto);
    void update(TaskDTO taskDTO);
    void delete(Long taskId);
}
