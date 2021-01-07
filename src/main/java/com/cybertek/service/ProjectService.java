package com.cybertek.service;

import com.cybertek.dto.ProjectDTO;

import java.util.List;


public interface ProjectService {
    ProjectDTO getByProjectCode(String projectCode);
    List<ProjectDTO> listAllProjects();
    void save(ProjectDTO projectDTO);
    void update(ProjectDTO projectDTO);
    void delete(String projectCode);
    void complete(String projectCode);
}
