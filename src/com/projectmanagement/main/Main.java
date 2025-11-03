package com.projectmanagement.main;

import com.projectmanagement.dao.ProjectRepositoryImpl;
import com.projectmanagement.entity.Employee;
import com.projectmanagement.entity.Project;
import com.projectmanagement.entity.Task;
import com.projectmanagement.exception.EmployeeNotFoundException;
import com.projectmanagement.exception.ProjectNotFoundException;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProjectRepositoryImpl repo = new ProjectRepositoryImpl();

        while (true) {
            try {
                System.out.println("\n===== PROJECT MANAGEMENT MENU =====");
                System.out.println("1. Create Project");
                System.out.println("2. Create Employee");
                System.out.println("3. Create Task");
                System.out.println("4. Assign Project to Employee");
                System.out.println("5. Assign Task in Project to Employee");
                System.out.println("6. View All Projects");
                System.out.println("7. View All Employees");
                System.out.println("8. View All Tasks");
                System.out.println("9. Search Project by ID");
                System.out.println("10. Delete Project");
                System.out.println("11. Delete Employee");
                System.out.println("12. View All Tasks for Employee in Project");
                System.out.println("13. Search Employee by ID");
                System.out.println("14. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // clear newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Project ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Project Name: ");
                        String pname = sc.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter Start Date (yyyy-mm-dd): ");
                        LocalDate date = LocalDate.parse(sc.nextLine());
                        System.out.print("Enter Status: ");
                        String status = sc.nextLine();

                        Project project = new Project(pid, pname, desc, date, status);
                        if (repo.createProject(project))
                            System.out.println("✅ Project created successfully!");
                        else
                            System.out.println("❌ Error inserting project. Check DB connection or constraints.");
                        break;

                    case 2:
                        System.out.print("Enter Employee ID: ");
                        int eid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Designation: ");
                        String desig = sc.nextLine();
                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = sc.nextDouble();
                        System.out.print("Enter Project ID: ");
                        int projId = sc.nextInt();

                        Employee emp = new Employee(eid, name, desig, gender, salary, projId);
                        if (repo.createEmployee(emp))
                            System.out.println("✅ Employee created successfully!");
                        else
                            System.out.println("❌ Error inserting employee. Check if project ID exists.");
                        break;

                    case 3:
                        System.out.print("Enter Task ID: ");
                        int tid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Task Name: ");
                        String tname = sc.nextLine();
                        System.out.print("Enter Project ID: ");
                        int projID = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int empID = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Task Status: ");
                        String tstatus = sc.nextLine();

                        Task task = new Task(tid, tname, projID, empID, tstatus);
                        if (repo.createTask(task))
                            System.out.println("✅ Task created successfully!");
                        else
                            System.out.println("❌ Error inserting task. Ensure project & employee exist.");
                        break;

                    case 4:
                        System.out.print("Enter Project ID to assign: ");
                        int assignProjId = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int assignEmpId = sc.nextInt();
                        if (repo.assignProjectToEmployee(assignProjId, assignEmpId))
                            System.out.println("✅ Project assigned to employee successfully!");
                        else
                            System.out.println("❌ Error assigning project. Verify IDs.");
                        break;

                    case 5:
                        System.out.print("Enter Task ID: ");
                        int assignTaskId = sc.nextInt();
                        System.out.print("Enter Project ID: ");
                        int assignProjectId = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int assignEmployeeId = sc.nextInt();
                        if (repo.assignTaskInProjectToEmployee(assignTaskId, assignProjectId, assignEmployeeId))
                            System.out.println("✅ Task assigned successfully!");
                        else
                            System.out.println("❌ Error assigning task.");
                        break;

                    case 6:
                        repo.viewAllProjects();
                        break;

                    case 7:
                        repo.viewAllEmployees();
                        break;

                    case 8:
                        repo.viewAllTasks();
                        break;

                    case 9:
                        System.out.print("Enter Project ID to search: ");
                        int searchId = sc.nextInt();
                        try {
                            Project found = repo.getProjectById(searchId);
                            System.out.println("✅ Project Found: " + found);
                        } catch (ProjectNotFoundException e) {
                            System.out.println("⚠️ " + e.getMessage());
                        }
                        break;

                    case 10:
                        System.out.print("Enter Project ID to delete: ");
                        int delPid = sc.nextInt();
                        if (repo.deleteProject(delPid))
                            System.out.println("✅ Project deleted successfully!");
                        else
                            System.out.println("❌ Error deleting project.");
                        break;

                    case 11:
                        System.out.print("Enter Employee ID to delete: ");
                        int delEid = sc.nextInt();
                        if (repo.deleteEmployee(delEid))
                            System.out.println("✅ Employee deleted successfully!");
                        else
                            System.out.println("❌ Error deleting employee.");
                        break;

                    case 12:
                        System.out.print("Enter Employee ID: ");
                        int empId = sc.nextInt();
                        System.out.print("Enter Project ID: ");
                        int projTaskId = sc.nextInt();
                        List<Task> tasks = repo.getAllTasks(empId, projTaskId);
                        if (tasks.isEmpty()) {
                            System.out.println("⚠️ No tasks found for this employee in this project.");
                        } else {
                            System.out.println("=== Tasks for Employee " + empId + " in Project " + projTaskId + " ===");
                            for (Task t : tasks) {
                                System.out.println(t);
                            }
                        }
                        break;

                    case 13:
                        System.out.print("Enter Employee ID to search: ");
                        int searchEmpId = sc.nextInt();
                        try {
                            Employee empFound = repo.getEmployeeById(searchEmpId);
                            System.out.println("✅ Employee Found: " + empFound);
                        } catch (EmployeeNotFoundException e) {
                            System.out.println("⚠️ " + e.getMessage());
                        }
                        break;

                    case 14:
                        System.out.println("🚀 Exiting Project Management System...");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input type! Please enter correct values.");
                sc.nextLine(); // clear invalid input
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }
}


