package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.exception.TicketingProjectException;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    UserRepository userRepository;
    UserMapper userMapper;


    ProjectService projectService;
    ProjectMapper projectMapper;
    TaskService taskService;
    TaskMapper taskMapper;

    /*
    @Lazy to fix the error by not creating the projectService bean when it is not needed
     */

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, ProjectMapper projectMapper, TaskService taskService, TaskMapper taskMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> list = userRepository.findAll(Sort.by("firstName"));
        return list.stream().map(obj -> {
            return userMapper.convertToDto(obj);
        }).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO dto) {
        User obj = userMapper.convertToEntity(dto);
        userRepository.save(obj);
    }

    @Override
    public UserDTO update(UserDTO dto) {

        //Find current user
        User user = userRepository.findByUserName(dto.getUserName());
        //Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(dto);
        //set id to the converted object
        convertedUser.setId(user.getId());
        //save updated user
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }


    @Override
    public void delete(String username) throws TicketingProjectException{
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new TicketingProjectException("User Does Not Exist.");
        }

        if(!checkIfUserCanBeDeleted(user)){
            throw new TicketingProjectException("User Can not be deleted. User is linked by a project or a task");

        }
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream().map(obj -> {
            return userMapper.convertToDto(obj);
        }).collect(Collectors.toList());
    }

    /*
   check manager or employee
   check if any task available
   check if all the tasks are completed
    */
    @Override
    public Boolean checkIfUserCanBeDeleted(User user) {

        switch (user.getRole().getDescription()) {
//            it needs to match to dropdown list in UI
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.readAllByManager(user);
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList =taskService.readAllTasksByEmployee(user);
                return taskDTOList.size() == 0;

        }
        return null;
    }

    //hard delete
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }
}