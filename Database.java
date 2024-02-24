
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

public class Database {

    public String reset = "\u001B[0m";
    public String red = "\u001B[31m";
    public String green = "\u001B[32m";
    private static Validator test = new Validator();
    private static final String url = "jdbc:mysql://localhost:3307/info";
    private static final String username = "root";
    private static final String Adminpassword = "Reem@9149";

    /**
     * Inserts an email and hashed password into the database file.
     *
     * @param email the email to be inserted
     * @param password the password to be hashed and inserted
     */
    public void insert(String email, String password) throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, Adminpassword);

            String insertQuery = "INSERT INTO userinfo (Email, Password) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, email);

            password = hash(password);

            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            
        } catch (ClassNotFoundException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        }
    }

    /**
     * Retrieves the hashed password associated with a given email from the
     * database file.
     *
     * @param email the email for which to retrieve the hashed password
     * @return the hashed password if found, otherwise null
     */
    public String retrieve(String email) throws ClassNotFoundException, SQLException {

        String password = "777";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, Adminpassword);

            String selectQuery = "SELECT Password FROM userinfo WHERE Email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
            
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (ClassNotFoundException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        }
        return password;
    }

    /**
     * Hashes the given password using the SHA-256 algorithm.
     *
     * @param password the password to be hashed
     * @return the hashed password
     */
    public String hash(String password) {

        String hashedPassword = "****";
        try {
            MessageDigest Hashed = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = Hashed.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        }
        return hashedPassword;
    }

    void getAccess() throws ClassNotFoundException, SQLException, IOException {
        System.out.print("Enter 1 for read databse\n2 for delete user database\n3 for read the log file: ");

        Scanner scan = new Scanner(System.in);
        String x = scan.nextLine();
        while (!test.isInt(x)) {
            System.out.println("Enter 1 or 2 or 3");
            x = scan.nextLine();
        }

        switch (Integer.parseInt(x)) {
            case 1:
                ReadDatabase();
                break;
            case 2:
                EditDatabase();
                break;
            case 3:
                Readlog();
                break;

        }
    }

    /**
     *
     * Reads and displays the content of the database file.
     *
     * @throws FileNotFoundException if the database file is not found
     *
     */
    private void ReadDatabase() throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, Adminpassword);

            String selectQuery = "SELECT Email, Password FROM userinfo";

            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String passwordValue = resultSet.getString("password");
                System.out.println("Email: " + email + ", Password: " + passwordValue);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        }
    }

    private void EditDatabase() throws ClassNotFoundException, SQLException {

        ReadDatabase();
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the email of student: ");
        String email = scan.nextLine();

        if (isThereEmail(email)) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, Adminpassword);

                Statement statement = connection.createStatement();
                String deleteQuery = "DELETE FROM userInfo WHERE email = '" + email + "'";
                statement.executeUpdate(deleteQuery);

                statement.close();
                connection.close();

            } catch (ClassNotFoundException e) {
                System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
                System.exit(0);
            } catch (SQLException e) {
                System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
                System.exit(0);
            }

        } else {
            System.out.println(red+"User not found!"+reset);
        }

    }

    public void log(String email) throws IOException {

        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TS = simpledateformat.format(new Date());

        String logMessage = TS + " - Email: " + email;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(red + "An error occurred. " + reset);
            System.exit(0);

        }
    }

    private void Readlog() throws IOException {
        
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String TS = simpledateformat.format(new Date());
        System.out.println("Tody date and time: " + TS);
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        }
    }

    /**
     * Checks if the given email exists in the database. If found, updates the
     * hashed password and returns true.
     *
     * @param email the email to check
     * @return true if the email exists in the database, false otherwise
     */
    public boolean isThereEmail(String email) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, Adminpassword);

            String selectQuery = "SELECT * FROM userinfo WHERE Email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean emailExists = resultSet.next();

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return emailExists;
        } catch (ClassNotFoundException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again");
            System.exit(0);
        }

        return false;
    }

