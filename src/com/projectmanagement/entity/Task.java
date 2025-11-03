package com.projectmanagement.entity;

public class Task {
    private int taskId;
    private String taskName;
    private int projectId;
    private int employeeId;
    private String status;

    // Default constructor
    public Task() {}

    // Parameterized constructor
    public Task(int taskId, String taskName, int projectId, int employeeId, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.status = status;
    }

    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", taskName=" + taskName +
                ", projectId=" + projectId + ", employeeId=" + employeeId +
                ", status=" + status + "]";
    }
}

