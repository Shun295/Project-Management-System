package com.projectmanagement.dao;

import com.projectmanagement.entity.Employee;
import com.projectmanagement.entity.Project;
import com.projectmanagement.entity.Task;
import com.projectmanagement.exception.ProjectNotFoundException;
import com.projectmanagement.exception.EmployeeNotFoundException;
import com.projectmanagement.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl implements IProjectRepository {

    private Connection conn;

    public ProjectRepositoryImpl() {
        conn = DBConnUtil.getConnection();
    }

    // ---------------- CREATE OPERATIONS ----------------

    @Override
    public boolean createEmployee(Employee emp) {
        String sql = "INSERT INTO Employee (id, name, designation, gender, salary, project_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, emp.getId());
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getDesignation());
            ps.setString(4, emp.getGender());
            ps.setDouble(5, emp.getSalary());
            ps.setInt(6, emp.getProjectId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error inserting Employee: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createProject(Project project) {
        String sql = "INSERT INTO Project (id, projectName, description, startDate, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, project.getId());
            ps.setString(2, project.getProjectName());
            ps.setString(3, project.getDescription());
            ps.setDate(4, Date.valueOf(project.getStartDate()));
            ps.setString(5, project.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error inserting Project: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createTask(Task task) {
        String sql = "INSERT INTO Task (task_id, task_name, project_id, employee_id, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, task.getTaskId());
            ps.setString(2, task.getTaskName());
            ps.setInt(3, task.getProjectId());
            ps.setInt(4, task.getEmployeeId());
            ps.setString(5, task.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error inserting Task: " + e.getMessage());
            return false;
        }
    }

    // ---------------- ASSIGNMENT OPERATIONS ----------------

    @Override
    public boolean assignProjectToEmployee(int projectId, int employeeId) {
        String sql = "UPDATE Employee SET project_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error assigning project: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean assignTaskInProjectToEmployee(int taskId, int projectId, int employeeId) {
        String sql = "UPDATE Task SET employee_id = ? WHERE task_id = ? AND project_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setInt(2, taskId);
            ps.setInt(3, projectId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error assigning task: " + e.getMessage());
            return false;
        }
    }

    // ---------------- DELETE OPERATIONS ----------------

    @Override
    public boolean deleteEmployee(int employeeId) {
        try (Connection con = DBConnUtil.getConnection()) {

            // Step 1: Delete all tasks assigned to this employee
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM task WHERE employee_id = ?");
            ps1.setInt(1, employeeId);
            ps1.executeUpdate();

            // Step 2: Delete the employee
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM employee WHERE id = ?");
            ps2.setInt(1, employeeId);
            int rows = ps2.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Employee deleted successfully!");
                return true;
            } else {
                System.out.println("⚠️ No employee found with ID " + employeeId);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error deleting Employee: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean deleteProject(int projectId) {
        try (Connection con = DBConnUtil.getConnection()) {

            // Step 1: Delete all tasks linked to this project
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM task WHERE project_id = ?");
            ps1.setInt(1, projectId);
            ps1.executeUpdate();

            // Step 2: Delete all employees linked to this project
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM employee WHERE project_id = ?");
            ps2.setInt(1, projectId);
            ps2.executeUpdate();

            // Step 3: Delete the project itself
            PreparedStatement ps3 = con.prepareStatement("DELETE FROM project WHERE id = ?");
            ps3.setInt(1, projectId);
            int rows = ps3.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Project deleted successfully!");
                return true;
            } else {
                System.out.println("⚠️ No project found with ID " + projectId);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error deleting Project: " + e.getMessage());
            return false;
        }
    }



    // ---------------- VIEW OPERATIONS ----------------

    @Override
    public void viewAllEmployees() {
        String sql = "SELECT * FROM Employee";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n=== EMPLOYEES ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Designation: " + rs.getString("designation") +
                        ", Gender: " + rs.getString("gender") +
                        ", Salary: " + rs.getDouble("salary") +
                        ", Project ID: " + rs.getInt("project_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewAllProjects() {
        String sql = "SELECT * FROM Project";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n=== PROJECTS ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("projectName") +
                        ", Description: " + rs.getString("description") +
                        ", Start Date: " + rs.getDate("startDate") +
                        ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewAllTasks() {
        String sql = "SELECT * FROM Task";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n=== TASKS ===");
            while (rs.next()) {
                System.out.println("Task ID: " + rs.getInt("task_id") +
                        ", Name: " + rs.getString("task_name") +
                        ", Project ID: " + rs.getInt("project_id") +
                        ", Employee ID: " + rs.getInt("employee_id") +
                        ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- SEARCH OPERATIONS ----------------

    @Override
    public Project getProjectById(int projectId) throws ProjectNotFoundException {
        String sql = "SELECT * FROM Project WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Project(
                        rs.getInt("id"),
                        rs.getString("projectName"),
                        rs.getString("description"),
                        rs.getDate("startDate").toLocalDate(),
                        rs.getString("status")
                );
            } else {
                throw new ProjectNotFoundException("Project with ID " + projectId + " not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProjectNotFoundException("Database error while fetching project.");
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("designation"),
                        rs.getString("gender"),
                        rs.getDouble("salary"),
                        rs.getInt("project_id")
                );
            } else {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EmployeeNotFoundException("Database error while fetching employee.");
        }
    }

    @Override
    public List<Task> getAllTasks(int empId, int projectId) throws EmployeeNotFoundException {
        // Ensure employee exists before fetching tasks
        Employee emp = getEmployeeById(empId);
        if (emp == null) {
            throw new EmployeeNotFoundException("Cannot fetch tasks. Employee with ID " + empId + " not found!");
        }

        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE employee_id = ? AND project_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setInt(2, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_name"),
                        rs.getInt("project_id"),
                        rs.getInt("employee_id"),
                        rs.getString("status")
                );
                tasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task getTaskById(int taskId) {
        String sql = "SELECT * FROM Task WHERE task_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Task(
                        rs.getInt("task_id"),
                        rs.getString("task_name"),
                        rs.getInt("project_id"),
                        rs.getInt("employee_id"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}




