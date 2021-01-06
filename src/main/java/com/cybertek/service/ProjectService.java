package com.cybertek.service;

import com.cybertek.dto.ProjectDTO;

import java.util.List;


public interface ProjectService {
    ProjectDTO getByProjectCode(String projectCode);
    List<ProjectDTO> listAllProjects();
    ProjectDTO save(ProjectDTO projectDTO);
    ProjectDTO update(ProjectDTO projectDTO);
    void delete(String projectCode);
}
