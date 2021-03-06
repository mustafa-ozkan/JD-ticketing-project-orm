package com.cybertek.service;

import com.cybertek.converter.UserDtoConverter;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;

import java.util.List;


public interface ProjectService {
    ProjectDTO getByProjectCode(String projectCode);
    List<ProjectDTO> listAllProjects();
    List<ProjectDTO> listAllProjectDetails();
    List<ProjectDTO> readAllByManager(User manager);
    void save(ProjectDTO projectDTO);
    void update(ProjectDTO projectDTO);
    void delete(String projectCode);
    void complete(String projectCode);
}
