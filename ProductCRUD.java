import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/shopdb";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con.setAutoCommit(false);

            int choice;
            do {
                System.out.println("\n--- Product Management ---");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addProduct(con, sc);
                    case 2 -> viewProducts(con);
                    case 3 -> updateProduct(con, sc);
                    case 4 -> deleteProduct(con, sc);
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice!");
                }

            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            String sql = "INSERT INTO Product VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);

            ps.executeUpdate();
            con.commit();
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            try {
                con.rollback();
                System.out.println("Transaction rolled back!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void viewProducts(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
        System.out.println("\n--- Product List ---");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt(1) +
                               ", Name: " + rs.getString(2) +
                               ", Price: " + rs.getDouble(3) +
                               ", Quantity: " + rs.getInt(4));
        }
    }

    private static void updateProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter new price: ");
            double price = sc.nextDouble();

            String sql = "UPDATE Product SET Price = ? WHERE ProductID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("No product found with that ID.");
            }
        } catch (Exception e) {
            try {
                con.rollback();
                System.out.println("Update failed, rolled back!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void deleteProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM Product WHERE ProductID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product not found!");
            }
        } catch (Exception e) {
            try {
                con.rollback();
                System.out.println("Delete failed, rolled back!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
