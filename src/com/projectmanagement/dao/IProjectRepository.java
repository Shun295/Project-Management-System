package com.projectmanagement.dao;

import com.projectmanagement.entity.Employee;
import com.projectmanagement.entity.Project;
import com.projectmanagement.entity.Task;
import com.projectmanagement.exception.EmployeeNotFoundException;
import com.projectmanagement.exception.ProjectNotFoundException;
import java.util.List;

public interface IProjectRepository {

    // ---------------- CREATE OPERATIONS ----------------
    boolean createEmployee(Employee emp);
    boolean createProject(Project project);
    boolean createTask(Task task);

    // ---------------- ASSIGNMENT OPERATIONS ----------------
    boolean assignProjectToEmployee(int projectId, int employeeId);
    boolean assignTaskInProjectToEmployee(int taskId, int projectId, int employeeId);

    // ---------------- DELETE OPERATIONS ----------------
    boolean deleteEmployee(int employeeId);
    boolean deleteProject(int projectId);

    // ---------------- VIEW OPERATIONS ----------------
    void viewAllEmployees();
    void viewAllProjects();
    void viewAllTasks();

    List<Task> getAllTasks(int empId, int projectId);

    // ---------------- SEARCH OPERATIONS ----------------
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;

    // throws custom exception if project not found
    Project getProjectById(int projectId) throws ProjectNotFoundException;

    Task getTaskById(int taskId);
}