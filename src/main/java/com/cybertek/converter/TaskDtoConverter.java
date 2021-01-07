package com.cybertek.converter;

import com.cybertek.dto.TaskDTO;
import com.cybertek.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class TaskDtoConverter implements Converter<String, TaskDTO> {

    //why do we need this we have two dropdowns in the view


    private TaskService taskService;

    public TaskDtoConverter(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskDTO convert(String source) {
        long id = Long.parseLong(source);
        return taskService.findById(id);
    }



}