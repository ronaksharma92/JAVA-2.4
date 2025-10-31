import java.sql.*;

public class EmployeeFetch {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb";
        String user = "root"; // replace with your MySQL username
        String password = "root"; // replace with your MySQL password

        try {
            // Step 1: Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish connection
            Connection con = DriverManager.getConnection(url, user, password);

            // Step 3: Create and execute query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

            // Step 4: Display records
            System.out.println("Employee Details:");
            System.out.println("----------------------------");
            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Salary: " + rs.getDouble("Salary"));
                System.out.println("----------------------------");
            }

            // Step 5: Close connection
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
