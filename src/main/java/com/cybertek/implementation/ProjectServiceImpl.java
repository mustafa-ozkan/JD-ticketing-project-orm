package com.cybertek.implementation;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;
import com.cybertek.enums.Status;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectMapper projectMapper;
    private ProjectRepository projectRepository;
    private UserMapper userMapper;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectRepository projectRepository, UserMapper userMapper) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ProjectDTO getByProjectCode(String projectCode) {
        return null;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> list = projectRepository.findAll(Sort.by("projectCode"));
        return list.stream().map(obj ->{return projectMapper.convertToDto(obj);}).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.OPEN);
        Project project= projectMapper.convertToEntity(projectDTO);
        project.setAssignedManager(userMapper.convertToEntity(projectDTO.getAssignedManager()));
        projectRepository.save(project);
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public void delete(String projectCode) {

    }
}
