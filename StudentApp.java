package view;

import controller.StudentDAO;
import model.Student;
import java.util.*;

public class StudentApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();
        int choice;

        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = sc.nextDouble();

                        dao.addStudent(new Student(id, name, dept, marks));
                        System.out.println("Student added successfully!");
                    }
                    case 2 -> dao.viewStudents();
                    case 3 -> {
                        System.out.print("Enter ID to update: ");
                        int id = sc.nextInt();
                        System.out.print("Enter new marks: ");
                        double marks = sc.nextDouble();
                        dao.updateStudentMarks(id, marks);
                        System.out.println("Marks updated!");
                    }
                    case 4 -> {
                        System.out.print("Enter ID to delete: ");
                        int id = sc.nextInt();
                        dao.deleteStudent(id);
                        System.out.println("Student deleted!");
                    }
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (choice != 5);

        sc.close();
    }
}
