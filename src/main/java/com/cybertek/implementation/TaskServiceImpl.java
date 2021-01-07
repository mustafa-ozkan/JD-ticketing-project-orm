package com.cybertek.implementation;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.enums.Status;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repository.TaskRepository;
import com.cybertek.service.TaskService;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskDTO findById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {

        List<Task> taskList = taskRepository.findAll();

        return taskList.stream().map(obj -> {
            return taskMapper.convertToDto(obj);
        }).collect(Collectors.toList());
    }

    @Override
    public Task save(TaskDTO taskDto) {
        //we do not have status on the view then we need to set
        taskDto.setTaskStatus(Status.OPEN);
        //we do not have assigneddate so we need to set
        taskDto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(taskDto);


        return taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {

        Optional<Task> task = taskRepository.findById(taskDTO.getId());

        Task convertedTask = taskMapper.convertToEntity(taskDTO);
        if (task.isPresent()) {
            convertedTask.setId(task.get().getId());
            convertedTask.setTaskStatus(task.get().getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }
    }

    @Override
    public void delete(Long taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        if (foundTask.isPresent()) {
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }

    }
}
