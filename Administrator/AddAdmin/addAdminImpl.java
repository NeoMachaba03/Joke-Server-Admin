package JokeServer.Administrator.AddAdmin;

import JokeServer.Administrator.Model.Admin;
import JokeServer.Database.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class addAdminImpl extends DBConfig implements addAdmin{

    private Connection c = getCon();
    private Scanner input = new Scanner(System.in);

    @Override
    public void newAdmin() {
        Admin admin = new Admin(0,"Stefan","Botha","theBeezneez45","StefanBotha");

        try (PreparedStatement ps = c.prepareStatement("INSERT INTO administrator(Name, Surname, Username, Password) VALUES(?, ?, ?, ?)")) {

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getSurname());
            ps.setString(3, admin.getUsername());
            ps.setString(4, admin.getPassword());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\nUser added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password){
        try (PreparedStatement statement = c.prepareStatement("SELECT * FROM administrator WHERE Username = ? AND Password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void Adminlogin(){
        while (true){
            try {
                System.out.print("\nEnter your username: ");
                String username = input.nextLine();

                System.out.print("\nEnter you password: ");
                String password = input.nextLine();

                boolean login = login(username,password);
                if (login == true){
                    System.out.println("\nYou have successfully logged in!!!");
                    break;
                }else {
                    System.out.println("\nUsername or password is incorrect!!! Please try again!!!");
                }
            }catch (InputMismatchException e){
                System.out.println("\nInvalid entry!!! Please try again");
            }
        }

    }

    public void run(){
        newAdmin();
    }

    public static void main(String[] args) {
        new addAdminImpl().run();
    }
}
